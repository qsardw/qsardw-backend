/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend;

import org.qsardw.backend.jobs.ProcessDataset;

import java.net.URL;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

import org.apache.log4j.helpers.Loader; 
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Main application. Launches the task scheduler that retrieves the enqueed 
 * requests and executes the processes.
 *
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public final class Daemon {
    
    private static final Integer EXECUTION_INTERVAL = 40;
    private static final Logger logger = Logger.getLogger(Daemon.class);

    /**
     * Class constructor. Private to avoid instatiation of the class
     */
    private Daemon() {}
    
    /**
     * Main method of the java process
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            URL log4jConfigFile = Loader.getResource("log4j.properties");
            PropertyConfigurator.configure(log4jConfigFile);
            
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

            Scheduler sched = schedFact.getScheduler();

            logger.debug("Initializing scheduler");
            sched.start();
            
            logger.debug("Initializing the job");
            JobDetail job = newJob(ProcessDataset.class).withIdentity("cleanDataset", "cleaning").build();
            
            logger.debug("Initializing the job trigger");
            Trigger trigger = newTrigger().withIdentity("cleaningTrigger", "cleaning").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(EXECUTION_INTERVAL).repeatForever()).build();

            logger.debug("Running the job using the trigger");
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException schException) {
            logger.error(schException.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}

