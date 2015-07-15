package org.qsardw.datamodel.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javiercaride
 */
@XmlRootElement
public class DatasetRawMoleculeGroup {
    private Integer id;
    private Integer dataset;
    private String groupSmile;
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
    
    public String getGroupSmile() {
        return groupSmile;
    }

    public void setGroupSmile(String groupSmile) {
        this.groupSmile = groupSmile;
    }    
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
