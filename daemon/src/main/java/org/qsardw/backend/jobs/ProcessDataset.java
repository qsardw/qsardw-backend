/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.jobs;

import org.qsardw.datamodel.beans.User;
import org.qsardw.datamodel.cleaners.DatasetCleaner;
import org.qsardw.datamodel.beans.Dataset;
import org.qsardw.datamodel.dao.DatasetDAO;
import org.qsardw.backend.readers.SdfLoader;

import org.qsardw.datamodel.dao.UserDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.qsardw.backend.readers.CsvLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Quartz Job that performs the cleaning of an Dataset
 *
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public final class ProcessDataset implements Job {

    public static final int PENDING_STATUS = 0;
    public static final int IN_PROCESS_STATUS = 1;
    public static final int PROCESSED_STATUS = 2;
    public static final int ERROR_STATUS = 3;
    
    public static final int SDF_FILE = 1;
    public static final int CSV_FILE = 2;
    
    static Logger logger = Logger.getLogger(ProcessDataset.class);
    private final DatasetDAO datasetDao;
    private final UserDAO userDao;

    /**
     * Class Constructor
     */
    public ProcessDataset() {
        this.datasetDao = new DatasetDAO();
        this.userDao = new UserDAO();
    }

    /**
     * Execute method that performs the cleaning process
     *
     * @param context Quartz context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.debug("Executing cleaning process");
            List<Dataset> pendingDatasets = this.datasetDao.selectPending();
            this.setProcessingStatus(pendingDatasets);
            this.process(pendingDatasets);
        } catch (Exception ex) {
            logger.debug("Throwing new JobeExecutionException " + ex.getMessage());
            throw new JobExecutionException(ex);
        }
    }
    
    /**
     * Sets the status of the datasets to IN_PROCESS_STATUS
     * 
     * @param pendingDatasets 
     */
    private void setProcessingStatus(List<Dataset> pendingDatasets) {
        for (Dataset dataset : pendingDatasets) {
            dataset.setStatus(ProcessDataset.IN_PROCESS_STATUS);
            this.datasetDao.update(dataset);
        }
    }
    
    private void process(List<Dataset> pendingDatasets) {
        for (Dataset dataset : pendingDatasets) {
            File fileToClean = new File(dataset.getOriginalFile());
            
            if (dataset.getFileType().equals(ProcessDataset.SDF_FILE)) {
                SdfLoader loader = new SdfLoader(fileToClean, dataset.getId());
                loader.readFile();
            } else {
                CsvLoader loader = new CsvLoader(fileToClean, dataset.getId());
                loader.readFile();
            }

            User owner = this.userDao.getById(dataset.getOwner());

            this.sendStartEmail(owner, dataset);
            DatasetCleaner cleaner = new DatasetCleaner(dataset.getId());
            logger.debug("Reading molecules to insert raw data for dataset " + dataset.getId());            
            cleaner.clean();
    
            dataset.setStatus(ProcessDataset.PROCESSED_STATUS);
            this.sendFinishedEmail(owner, dataset);
            logger.debug("Setting processed status to dataset " + dataset.getId());
            this.datasetDao.update(dataset);
        }
    }

    private void sendEmail(String recipient, String subject, String body) {
        try {
            logger.debug("Trying to send email to " + recipient);

            logger.debug("Loading session.properties");
            final Properties sessionProperties = new Properties();
            InputStream sessionStream = getClass().getClassLoader().getResourceAsStream("session.properties");
            sessionProperties.load(sessionStream);
            sessionStream.close();

            logger.debug("mail_credentials.properties");
            final Properties mailConfigProperties = new Properties();
            InputStream configStream = getClass().getClassLoader().getResourceAsStream("mail_credentials.properties");
            mailConfigProperties.load(configStream);
            configStream.close();

            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            mailConfigProperties.getProperty("mail.username"),
                            mailConfigProperties.getProperty("mail.password")
                    );
                }
            };

            logger.debug(sessionProperties.getProperty("mail.smtp.host"));
            logger.debug(mailConfigProperties.getProperty("mail.username"));
            logger.debug(mailConfigProperties.getProperty("mail.password"));

            Session session = Session.getInstance(sessionProperties, auth);

            InternetAddress[] toAddresses = {new InternetAddress(recipient)};

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailConfigProperties.getProperty("mail.from")));
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent(body, "text/html");

            Transport.send(message);
        } catch (FileNotFoundException ex) {
            logger.debug("Sending email to " + recipient + " failed: file not found");
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            logger.debug("Sending email to " + recipient + " failed: IO failed");
            logger.error(ex.getMessage());
        } catch (Exception ex) {
            logger.debug("Sending email to " + recipient + " failed: general fail");
            logger.error(ex.getMessage());
            logger.error(ex.getLocalizedMessage());
            logger.error(ex.toString());
            ex.printStackTrace();
        }
    }

    private void sendStartEmail(User owner, Dataset dataset) {
        String subject = "The dataset " + dataset.getDatasetName() + " has started to be cleaned";
        String mailMessage = "<html>";
        mailMessage += "<head><title>" + subject + "</title></head>";
        mailMessage += "<body>";
        mailMessage += "<h1>Hi " + owner.getCompleteName() + "!</h1>";
        mailMessage += "<p>Your dataset <em>\"" + dataset.getDatasetName() + "\"</em> with ID " + dataset.getId();
        mailMessage += "has started to be cleaned. You will receive an email when it has been totally cleaned</p>";
        mailMessage += "<br/><br/><p>Regards, The QSARDW App</p>";
        mailMessage += "</body>";
        mailMessage += "</html>";

        this.sendEmail(owner.getUsername(), subject, mailMessage);
    }

    private void sendFinishedEmail(User owner, Dataset dataset) {
        String subject = "The dataset " + dataset.getDatasetName() + " has just be cleaned";
        String mailMessage = "<html>";
        mailMessage += "<head><title>" + subject + "</title></head>";
        mailMessage += "<body>";
        mailMessage += "<h1>Hi again " + owner.getCompleteName() + "!</h1>";
        mailMessage += "<p>Your dataset <em>\"" + dataset.getDatasetName() + "\"</em> with ID " + dataset.getId();
        mailMessage += "has just be cleaned.</p>";
        mailMessage += "<p>You can perform a manual review at <a href=\"http://app.qsardw.org\"> your panel</a></p>";
        mailMessage += "<br/><br/><p>Regards, The QSARDW App</p>";
        mailMessage += "</body>";
        mailMessage += "</html>";

        this.sendEmail(owner.getUsername(), subject, mailMessage);
    }
}
