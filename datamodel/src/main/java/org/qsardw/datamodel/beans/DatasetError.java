package org.qsardw.datamodel.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for managing the clean dataset errors
 * 
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
@XmlRootElement
public class DatasetError {
    private Integer id;
    private Integer dataset;
    private Integer molecule;
    private String errorMessage;
    private Date timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataset() {
        return dataset;
    }

    public void setDataset(Integer dataset) {
        this.dataset = dataset;
    }

    public Integer getMolecule() {
        return molecule;
    }

    public void setMolecule(Integer molecule) {
        this.molecule = molecule;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
