/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.qsardw.datamodel.beans.DatasetRawMolecule;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class DatasetRawMoleculesDAO extends BaseDAO {
    
    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public DatasetRawMoleculesDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }
    
    /**
     * Adds a new molecule in the dataset_raw_molecules table
     * @param datasetRawMolecule bean with the information to be saved
     */
    public void create(DatasetRawMolecule datasetRawMolecule) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("DatasetRawMolecules.insert", datasetRawMolecule);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    public void update(DatasetRawMolecule datasetRawMolecule) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("DatasetRawMolecules.update", datasetRawMolecule);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    public void keepGroupedMolecule(DatasetRawMolecule datasetRawMolecule) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.delete("DatasetRawMolecules.keepGroupedMolecule", datasetRawMolecule);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns a raw molecule by Id
     * @param id
     * @return 
     */
    public DatasetRawMolecule selectById(Integer id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            
            DatasetRawMolecule filter = new DatasetRawMolecule();
            filter.setId(id);

            DatasetRawMolecule result = (DatasetRawMolecule) session.selectOne("DatasetRawMolecules.selectById", filter);

            return result;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectBySmile(Integer datasetId, String smile) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        filter.setSmile(smile);
        return this.selectFiltered(filter, "DatasetRawMolecules.selectBySmile");
    }
    
    public List<DatasetRawMolecule> selectByInchi(Integer datasetId, String inchi) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        filter.setInchi(inchi);
        return this.selectFiltered(filter, "DatasetRawMolecules.selectByInchi");
    }
    
    public List<DatasetRawMolecule> selectByInchiKey(Integer datasetId, String inchiKey) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        filter.setInchiKey(inchiKey);
        return this.selectFiltered(filter, "DatasetRawMolecules.selectByInchiKey");
    }
    
    public List<DatasetRawMolecule> selectByDataset(Integer datasetId) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        return this.selectFiltered(filter, "DatasetRawMolecules.selectByDataset");
    }
    
    public List<DatasetRawMolecule> selectByMoleculesGroup(Integer datasetId, Integer moleculesGroup, int limit, int offset) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        filter.setMoleculesGroup(moleculesGroup);
        filter.setStatus(2);
        
        return this.selectFiltered(filter, "DatasetRawMolecules.selectByMoleculesGroup", limit, offset);
    }
    
    public int countCleanMolecules(Integer datasetId) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            return session.selectOne("DatasetRawMolecules.countCleanMolecules", datasetId);
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectCleanMolecules(Integer datasetId) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = session.selectList("DatasetRawMolecules.selectCleanMolecules", datasetId);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectCleanMolecules(Integer datasetId, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = null;
            if ((limit > 0) && (offset > 0)) {
                RowBounds bounds = new RowBounds(offset, limit);
                list = session.selectList("DatasetRawMolecules.selectCleanMolecules", datasetId, bounds);
            } else {
                list = session.selectList("DatasetRawMolecules.selectCleanMolecules", datasetId);
            }
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectInchiKeyDuplicates(Integer datasetId, String inchiKey, int limit, int offset) {
        DatasetRawMolecule filter = new DatasetRawMolecule();
        filter.setDataset(datasetId);
        filter.setInchiKey(inchiKey);
        return this.selectFiltered(filter, "DatasetRawMolecules.selectDuplicatesByInchiKey", limit, offset);
    }
    
    public List<DatasetRawMolecule> selectDiscardedMolecules(Integer datasetId) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = session.selectList("DatasetRawMolecules.selectDiscardedMolecules", datasetId);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectDiscardedMolecules(Integer datasetId, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = null;
            if ((limit > 0) && (offset > 0)) {
                RowBounds bounds = new RowBounds(offset, limit);
                list = session.selectList("DatasetRawMolecules.selectDiscardedMolecules", datasetId, bounds);
            } else {
                list = session.selectList("DatasetRawMolecules.selectDiscardedMolecules", datasetId);
            }
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectMoleculesToReview(Integer datasetId) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = session.selectList("DatasetRawMolecules.selectMoleculesToReview", datasetId);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectMoleculesToReview(Integer datasetId, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = null;
            if ((limit > 0) && (offset > 0)) {
                RowBounds bounds = new RowBounds(offset, limit);
                list = session.selectList("DatasetRawMolecules.selectMoleculesToReview", datasetId, bounds);
            } else {
                list = session.selectList("DatasetRawMolecules.selectMoleculesToReview", datasetId);
            }
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectMultipleMoleculesToReview(Integer datasetId) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = session.selectList("DatasetRawMolecules.selectMultipleMoleculesToReview", datasetId);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<DatasetRawMolecule> selectMultipleMoleculesToReview(Integer datasetId, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            List<DatasetRawMolecule> list = null;
            if ((limit > 0) && (offset > 0)) {
                RowBounds bounds = new RowBounds(offset, limit);
                list = session.selectList("DatasetRawMolecules.selectMultipleMoleculesToReview", datasetId, bounds);
            } else {
                list = session.selectList("DatasetRawMolecules.selectMultipleMoleculesToReview", datasetId);
            }
            return list;
        } finally {
            session.close();
        }
    }
    
    protected List<DatasetRawMolecule> selectFiltered(DatasetRawMolecule filter, String query) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<DatasetRawMolecule> list = session.selectList(query, filter);
            return list;
        } finally {
            session.close();
        }
    }
    
    protected List<DatasetRawMolecule> selectFiltered(DatasetRawMolecule filter, String query, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<DatasetRawMolecule> list = null;
            if ((limit > 0) && (offset > 0)) {
                RowBounds bounds = new RowBounds(offset, limit);
                list = session.selectList(query, filter, bounds);
            } else {
                list = session.selectList(query, filter);
            }
            return list;
        } finally {
            session.close();
        }
    }
}
