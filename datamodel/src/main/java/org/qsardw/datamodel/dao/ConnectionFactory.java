/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.apache.log4j.Logger;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class ConnectionFactory {
    private static volatile ConnectionFactory factoryInstance = null;
    private SqlSessionFactory sessionFactory;
    private static Logger logger = Logger.getLogger(ConnectionFactory.class);

    /**
     * Class constructor. Private to ensure that only one instance of the 
     * object is instantiated
     */
    private ConnectionFactory() {
        try {

            String resource = "mybatis/mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);

            sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        } catch (FileNotFoundException fileNotFoundException) {
            logger.error(fileNotFoundException.getMessage());
        } catch (IOException iOException) {
            logger.error(iOException.getMessage());
        }
    }

    /**
     * Public method to retrieve the unique SqlSessionFactory object
     * @return SqlSessionFactory object to manage DB connections
     */
    public static synchronized SqlSessionFactory getSqlSessionFactory() {

        if (factoryInstance == null) {
            factoryInstance = new ConnectionFactory();
        }

        return factoryInstance.getSessionFactory();
    }

    /**
     * @return the sessionFactory
     */
    public SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
