/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.cleaners;

import org.qsardw.datamodel.beans.Dataset;
import org.qsardw.datamodel.beans.DatasetProcessedMolecule;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.dao.DatasetProcessedMoleculesDAO;
import org.qsardw.datamodel.dao.DatasetDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;
import java.util.List;
import org.qsardw.datamodel.dao.DatasetRawMoleculesGroupsDAO;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class DatasetCleaner {

    private static final int ON_DUPLICATE_REVIEW = 1;
    private static final int ON_DUPLICATE_DISCARD = 2;
    private static final int ON_DUPLICATE_KEEP_FIRST = 3;
    private static final int CLEAN_MOLECULE = 1;
    private static final int DISCARDED_MOLECULE = 2;
    private static final int TO_REVIEW_MOLECULE = 3;
    private static final int TO_DELETE_MOLECULE = 4;
    
    private Integer datasetId;
    private DatasetProcessedMoleculesDAO processedMoleculesDao;
    private DatasetRawMoleculesDAO moleculesDao;
    private DatasetDAO datasetDao;
    private Dataset dataset;

    public DatasetCleaner(Integer datasetId) {
        this.setDatasetId(datasetId);
        this.processedMoleculesDao = new DatasetProcessedMoleculesDAO();
        this.moleculesDao = new DatasetRawMoleculesDAO();
        this.datasetDao = new DatasetDAO();
        this.dataset = this.datasetDao.getById(this.datasetId);
    }

    public void clean() {
        List<DatasetRawMolecule> molecules = this.moleculesDao.selectByDataset(this.getDatasetId());

        for (DatasetRawMolecule rawMolecule : molecules) {
            this.findDuplicates(rawMolecule);
        }
    }

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public DatasetRawMoleculesDAO getMoleculesDao() {
        return moleculesDao;
    }

    public void setMoleculesDao(DatasetRawMoleculesDAO moleculesDao) {
        this.moleculesDao = moleculesDao;
    }

    public DatasetProcessedMolecule findDuplicates(DatasetRawMolecule molecule) {
        List<DatasetRawMolecule> duplicatedMolecules = this.moleculesDao.selectByInchiKey(datasetId, molecule.getInchiKey());

        DatasetProcessedMolecule processedMolecule = null;
        
        if (duplicatedMolecules.size() > 1) {
            for (DatasetRawMolecule duplicatedMolecule : duplicatedMolecules) {
                duplicatedMolecule.setIsDuplicated(Boolean.TRUE);
                this.moleculesDao.update(duplicatedMolecule);
            }

            switch (this.dataset.getOnDuplicatesStrategy()) {
                case ON_DUPLICATE_KEEP_FIRST:
                    processedMolecule = this.keepFirstMoleculeFound(molecule);
                    break;
                case ON_DUPLICATE_DISCARD:
                    processedMolecule = this.addDiscardedMolecule(molecule);
                    break;
                case ON_DUPLICATE_REVIEW:
                default:
                    processedMolecule = this.addToReviewMolecule(molecule);
                    break;
            }
        } else {
            processedMolecule = this.addCleanMolecule(molecule);
        }
        
        if (molecule.getStatus() == DatasetRawMolecule.REVIEW_MULTIPLE) {
            
            this.moleculesDao.keepGroupedMolecule(molecule);
            Integer groupToDelete = molecule.getMoleculesGroup();
            
            molecule.setMoleculesGroup(0);
            this.moleculesDao.update(molecule);
            
            DatasetRawMoleculesGroupsDAO groups = new DatasetRawMoleculesGroupsDAO();
            groups.deleteByGroupId(groupToDelete);
        }
        
        return processedMolecule;
    }

    /**
     * If the raw molecule is the first molecule with the same inchi key it is set as clean. If not
     * it is set as a discarded one
     * 
     * @param molecule Raw molecule to be clean
     * @return 
     */
    private DatasetProcessedMolecule keepFirstMoleculeFound(DatasetRawMolecule molecule) {
        DatasetProcessedMolecule filter = new DatasetProcessedMolecule();
        filter.setInchiKey(molecule.getInchiKey());
        filter.setDataset(this.getDatasetId());
        filter.setProcessedStatus(CLEAN_MOLECULE);
        
        DatasetProcessedMolecule existingMolecule = this.processedMoleculesDao.getFiltered(filter);

        if (existingMolecule == null) {
            return this.addCleanMolecule(molecule);
        } else {
            return this.addDiscardedMolecule(molecule);
        }
    }
    
    /**
     * Adds a molecule as a clean molecule
     * @param molecule
     * @return 
     */
    private DatasetProcessedMolecule addCleanMolecule(DatasetRawMolecule molecule) {
        return this.addProcessedMolecule(molecule, CLEAN_MOLECULE);
    }
    
    /**
     * Sets the molecule as a discarded molecule
     * 
     * @param molecule raw molecule to be discarded
     * @return 
     */
    private DatasetProcessedMolecule addDiscardedMolecule(DatasetRawMolecule molecule) {
        return this.addProcessedMolecule(molecule, DISCARDED_MOLECULE);
    }
    
    /**
     * Sets the molecule as a molecule to be reviewed
     * 
     * @param molecule raw molecule to be reviewed
     * @return 
     */
    private DatasetProcessedMolecule addToReviewMolecule(DatasetRawMolecule molecule) {
        return this.addProcessedMolecule(molecule, TO_REVIEW_MOLECULE);
    }
    
    /**
     * Adds a molecule to the processed molecules table
     * 
     * @param molecule raw molecule to be processed
     * @param status status that shows the result or the cleaning process
     * @return 
     */
    private DatasetProcessedMolecule addProcessedMolecule(DatasetRawMolecule molecule, Integer status) {
        DatasetProcessedMolecule newProcessedMolecule = new DatasetProcessedMolecule();
        newProcessedMolecule.setDataset(molecule.getDataset());
        newProcessedMolecule.setMolecule(molecule.getId());
        newProcessedMolecule.setInchiKey(molecule.getInchiKey());
        newProcessedMolecule.setProcessedStatus(status);

        this.processedMoleculesDao.create(newProcessedMolecule);
        
        return newProcessedMolecule;
    }
}
