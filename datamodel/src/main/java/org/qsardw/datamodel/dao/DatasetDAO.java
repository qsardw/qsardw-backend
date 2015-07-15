/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.dao;

import org.qsardw.datamodel.beans.Dataset;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class DatasetDAO extends BaseDAO {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public DatasetDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }
    
    /**
     * Creates a new dataset in DB
     * 
     * @param dataset 
     */
    public void create(Dataset dataset) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("Dataset.insert", dataset);
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Returns the list of all dataset table rows
     *
     * @return the list of all Dataset beans
     */
    public List<Dataset> selectAll() {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            List<Dataset> list = session.selectList("Dataset.getAll");
            return list;
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns the list of all dataset table rows
     *
     * @return the list of all Dataset beans
     */
    public List<Dataset> selectPending() {

        Dataset filter = new Dataset();
        filter.setStatus(Dataset.PENDING_STATUS);
        
        return this.selectFiltered(filter);
    }
    
    public List<Dataset> selectByOwner(Integer owner)
    {
        Dataset filter = new Dataset();
        filter.setOwner(owner);
        
        return this.selectFiltered(filter);
    }

    /**
     * Returns the list of all dataset table rows that match the filter
     * conditions
     *
     * @param filter an instance of Dataset bean with filtering information
     * @return the list of all Dataset beans that matches the filter
     */
    public List<Dataset> selectFiltered(Dataset filter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<Dataset> list = session.selectList("Dataset.selectFiltered", filter);

            return list;
        } finally {
            session.close();
        }
    }

    /**
     * Updates a row of the dataset DB table
     *
     * @param datasetObj Dataset bean with information to be updated
     * @return the number of rows updated
     */
    public int update(Dataset datasetObj) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int result = session.update("Dataset.update", datasetObj);
            session.commit();

            return result;
        } finally {
            session.close();
        }
    }

    /**
     * Returns the Dataset table row that matches the filter by name
     *
     * @param id the id to retrieve the row from DB
     * @return the Dataset bean that matches the filter
     */
    public Dataset getById(Integer id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            Dataset filter = new Dataset();
            filter.setId(id);

            Dataset result = (Dataset) session.selectOne("Dataset.selectById", filter);

            return result;
        } finally {
            session.close();
        }
    }
}
