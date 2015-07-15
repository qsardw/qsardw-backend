
package org.qsardw.datamodel.dao;

import org.qsardw.datamodel.beans.DatasetError;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author javiercaride
 */
public class DatasetErrorsDAO extends BaseDAO {
    
    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public DatasetErrorsDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }
    
    /**
     * Adds a new error in the dataset_errors table
     * @param datasetError bean with the information to be saved
     */
    public void create(DatasetError datasetError) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("DatasetErrors.insert", datasetError);
            session.commit();
        } finally {
            session.close();
        }
    } 
}
