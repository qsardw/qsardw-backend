package org.qsardw.datamodel.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.qsardw.datamodel.beans.User;

/**
 * Created by javiercaride on 14/07/15.
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
