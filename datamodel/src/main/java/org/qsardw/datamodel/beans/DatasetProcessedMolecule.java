package org.qsardw.datamodel.beans;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
@XmlRootElement
public class DatasetProcessedMolecule {
    
    public static final int CLEAN_MOLECULE = 1;
    public static final int DISCARDED_MOLECULE = 2;
    public static final int TO_REVIEW_MOLECULE = 3;
    public static final int TO_DELETE_MOLECULE = 4;
    
    private Integer dataset;
    private Integer molecule;
    private String inchiKey;
    private Integer processedStatus;
    private Timestamp ts;

    public Integer getDataset() {
        return dataset;
    }

    public void setDataset(Integer dataset) {
        this.dataset = dataset;
    }
    
    public String getInchiKey() {
        return inchiKey;
    }

    public void setInchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
    }

    public Integer getMolecule() {
        return molecule;
    }

    public void setMolecule(Integer molecule) {
        this.molecule = molecule;
    }

    public Timestamp getTs() {
        return ts;
    }

    public Integer getProcessedStatus() {
        return processedStatus;
    }

    public void setProcessedStatus(Integer processedStatus) {
        this.processedStatus = processedStatus;
    }
    
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }    
}
