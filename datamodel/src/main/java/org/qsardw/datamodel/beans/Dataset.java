/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.beans;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
@XmlRootElement
public class Dataset {
    
    public static final int PENDING_STATUS = 0;
    public static final int IN_PROCESS_STATUS = 1;
    public static final int PROCESSED_STATUS = 2;
    public static final int ERROR_STATUS = 3;
    
    private Integer id;
    private String datasetName;
    private String description;
    private String originalFile;
    private Integer fileType;
    private Integer initialMolecules;
    private Integer distinctMolecules;
    private Boolean isCleaned;
    private Integer owner;
    private Timestamp createdOn;
    private Integer multipleMoleculesStrategy;
    private Integer onDuplicatesStrategy;
    private Integer status;
    private Integer visibility;
    private Timestamp ts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(String originalFile) {
        this.originalFile = originalFile;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getInitialMolecules() {
        return initialMolecules;
    }

    public void setInitialMolecules(Integer initialMolecules) {
        this.initialMolecules = initialMolecules;
    }

    public Integer getDistinctMolecules() {
        return distinctMolecules;
    }

    public void setDistinctMolecules(Integer distinctMolecules) {
        this.distinctMolecules = distinctMolecules;
    }

    public Boolean isIsCleaned() {
        return isCleaned;
    }

    public void setIsCleaned(Boolean isCleaned) {
        this.isCleaned = isCleaned;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getMultipleMoleculesStrategy() {
        return multipleMoleculesStrategy;
    }

    public void setMultipleMoleculesStrategy(Integer multipleMoleculesStrategy) {
        this.multipleMoleculesStrategy = multipleMoleculesStrategy;
    }

    public Integer getOnDuplicatesStrategy() {
        return onDuplicatesStrategy;
    }

    public void setOnDuplicatesStrategy(Integer onDuplicatesStrategy) {
        this.onDuplicatesStrategy = onDuplicatesStrategy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}
