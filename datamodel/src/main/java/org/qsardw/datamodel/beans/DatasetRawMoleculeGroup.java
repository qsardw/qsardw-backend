/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
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
