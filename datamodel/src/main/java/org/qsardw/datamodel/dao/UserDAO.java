/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.qsardw.datamodel.beans.User;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class UserDAO extends BaseDAO {
    private SqlSessionFactory sqlSessionFactory;

    /**
     * Class constructor
     */
    public UserDAO() {
        sqlSessionFactory = ConnectionFactory.getSqlSessionFactory();
    }

    /**
     * Returns the User table row that matches the filter by name
     *
     * @param id the id to retrieve the row from DB
     * @return the Dataset bean that matches the filter
     */
    public User getById(Integer id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            User filter = new User();
            filter.setId(id);

            User result = (User) session.selectOne("User.selectById", filter);

            return result;
        } finally {
            session.close();
        }
    }
}
