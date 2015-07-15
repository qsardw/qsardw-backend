
package org.qsardw.datamodel.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.qsardw.datamodel.beans.DatasetRawMoleculeGroup;

/**
 *
 * @author javiercaride
 */
public class DatasetRawMoleculesGroupsDAO extends BaseDAO {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public DatasetRawMoleculesGroupsDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }
    
    public void create(DatasetRawMoleculeGroup group) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("DatasetRawMoleculesGroups.insert", group);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    public void deleteByGroupId(Integer groupId) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("DatasetRawMoleculesGroups.deleteById", groupId);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    /**
     * Select the group molecules filtered by dataset id
     * 
     * @param datasetId
     * @return 
     */
    public List<DatasetRawMoleculeGroup> selectByDataset(Integer datasetId) {
        DatasetRawMoleculeGroup filter = new DatasetRawMoleculeGroup();
        filter.setDataset(datasetId);
        return this.selectFiltered(filter, "DatasetRawMoleculesGroups.selectByDataset");
    }
    
    /**
     * Select the group molecules filtered by dataset id
     * 
     * @param datasetId
     * @param limit
     * @param offset
     * @return 
     */
    public List<DatasetRawMoleculeGroup> selectByDataset(Integer datasetId, int limit, int offset) {
        DatasetRawMoleculeGroup filter = new DatasetRawMoleculeGroup();
        filter.setDataset(datasetId);
        return this.selectFiltered(filter, "DatasetRawMoleculesGroups.selectByDataset", limit, offset);
    }
    
    /**
     * Select raw molecule groups filtered by a DatasetRawMoleculeGroup bean
     * 
     * @param filter
     * @param query
     * @return 
     */
    protected List<DatasetRawMoleculeGroup> selectFiltered(DatasetRawMoleculeGroup filter, String query) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<DatasetRawMoleculeGroup> list = session.selectList(query, filter);
            return list;
        } finally {
            session.close();
        }
    }
    
    /**
     * Select raw molecule groups filtered by a DatasetRawMoleculeGroup bean with bounds
     * 
     * @param filter
     * @param query
     * @param limit
     * @param offset
     * @return 
     */
    protected List<DatasetRawMoleculeGroup> selectFiltered(DatasetRawMoleculeGroup filter, String query, int limit, int offset) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<DatasetRawMoleculeGroup> list = null;
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
