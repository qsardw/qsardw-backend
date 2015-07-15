package org.qsardw.datamodel.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javiercaride
 */
@XmlRootElement
public class DatasetRawMolecule {
    
    public static final int TO_CLEAN = 1;
    public static final int REVIEW_MULTIPLE = 2;
    public static final int PROCESSED = 3;
    
    private Integer id;
    private Integer dataset;
    private Integer moleculeNumber;
    private String smile;
    private String inchi;
    private String inchiKey;
    private String sourceId;
    private String sourcePublication;
    private String sourceName;
    private Boolean isDuplicated;
    private Integer status;
    private Integer moleculesGroup;
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

    public Integer getMoleculeNumber() {
        return moleculeNumber;
    }

    public void setMoleculeNumber(Integer moleculeNumber) {
        this.moleculeNumber = moleculeNumber;
    }

    public String getSmile() {
        return smile;
    }

    public void setSmile(String smile) {
        this.smile = smile;
    }

    public String getInchi() {
        return inchi;
    }

    public void setInchi(String inchi) {
        this.inchi = inchi;
    }

    public String getInchiKey() {
        return inchiKey;
    }

    public void setInchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourcePublication() {
        return sourcePublication;
    }

    public void setSourcePublication(String sourcePublication) {
        this.sourcePublication = sourcePublication;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Boolean getIsDuplicated() {
        return isDuplicated;
    }

    public void setIsDuplicated(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getMoleculesGroup() {
        return moleculesGroup;
    }

    public void setMoleculesGroup(Integer moleculesGroup) {
        this.moleculesGroup = moleculesGroup;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
