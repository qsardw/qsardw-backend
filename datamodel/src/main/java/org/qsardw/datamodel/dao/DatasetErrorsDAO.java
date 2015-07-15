/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.dao;

import org.qsardw.datamodel.beans.DatasetError;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
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
