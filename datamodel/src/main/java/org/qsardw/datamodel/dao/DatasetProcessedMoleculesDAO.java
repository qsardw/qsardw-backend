
package org.qsardw.datamodel.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.qsardw.datamodel.beans.DatasetProcessedMolecule;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class DatasetProcessedMoleculesDAO extends BaseDAO {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public DatasetProcessedMoleculesDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }

    /**
     * Adds a new clean molecule in the dataset_clean_molecules table
     *
     * @param datasetCleanMolecule bean with the information to be saved
     */
    public void create(DatasetProcessedMolecule datasetCleanMolecule) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("DatasetProcessedMolecules.insert", datasetCleanMolecule);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns the dataset_clean_molecules row that matches the PK
     * @param molecule Id of the molecule
     * @return 
     */
    public DatasetProcessedMolecule read(int molecule) {
        SqlSession session = sqlSessionFactory.openSession();

        try {        
            DatasetProcessedMolecule filter = new DatasetProcessedMolecule();
            filter.setMolecule(molecule);

            DatasetProcessedMolecule result = (DatasetProcessedMolecule) session.selectOne("DatasetProcessedMolecules.selectOne", filter);

            return result;
        } finally {
            session.close();
        }
    }
    
    /**
     * Updates a row of the dataset_clean_molecules DB table
     *
     * @param processedMolecule Dataset processed bean with information to be updated
     * @return the number of rows updated
     */
    public int update(DatasetProcessedMolecule processedMolecule) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("DatasetProcessedMolecules.update", processedMolecule);
            session.commit();

            return result;
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns the processed molecule filtering by molecule id
     * 
     * @param moleculeId
     * @return 
     */
    public DatasetProcessedMolecule getByMoleculeId(Integer moleculeId) {
        DatasetProcessedMolecule filter = new DatasetProcessedMolecule();
        filter.setMolecule(moleculeId);
        
        return this.getFiltered(filter);
    }
    
    /**
     * Returns the processed molecule filtering by dataset id
     * 
     * @param datasetId
     * @return 
     */
    public List<DatasetProcessedMolecule> getByDatasetId(Integer datasetId) {
        DatasetProcessedMolecule filter = new DatasetProcessedMolecule();
        filter.setDataset(datasetId);
        
        return this.getFilteredMolecules(filter, "DatasetProcessedMolecules.selectFiltered");
    }
    
    /**
     * Returns the processed molecule filtering by dataset id
     * 
     * @param datasetId
     * @return 
     */
    public List<DatasetProcessedMolecule> getCleanByDatasetId(Integer datasetId) {
        DatasetProcessedMolecule filter = new DatasetProcessedMolecule();
        filter.setDataset(datasetId);
        filter.setProcessedStatus(DatasetProcessedMolecule.CLEAN_MOLECULE);
        
        return this.getFilteredMolecules(filter, "DatasetProcessedMolecules.selectFiltered");
    }
    
    /**
     * Returns the DatasetProcessedMolecule row that matches the filter
     *
     * @param filter the DatasetPRocessedMolecule with the filter configured
     * @return the DatasetProcessedMolecule bean that matches the filter
     */
    public DatasetProcessedMolecule getFiltered(DatasetProcessedMolecule filter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            DatasetProcessedMolecule result = (DatasetProcessedMolecule) session.selectOne("DatasetProcessedMolecules.selectFiltered", filter);

            return result;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetProcessedMolecule> getFilteredMolecules(
        DatasetProcessedMolecule filter,
        String query
    ) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<DatasetProcessedMolecule> list = session.selectList(query, filter);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetProcessedMolecule> getFilteredMolecules(
        DatasetProcessedMolecule filter,
        String query, 
        int limit, 
        int offset
    ) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            RowBounds bounds = new RowBounds(offset, limit);
            List<DatasetProcessedMolecule> list = session.selectList(query, filter, bounds);
            
            return list;
        } finally {
            session.close();
        }
    }

}
