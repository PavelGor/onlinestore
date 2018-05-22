package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.UserMapper;
import com.gordeev.onlinestore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcUserDao implements UserDao {

    /**
     * JDBC Driver and database url
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_DATABASE_URL = "jdbc:mysql://localhost/onlinestore?useUnicode=true&characterEncoding=UTF8";
    /**
     * User and Password
     */
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final UserMapper USER_MAPPER = new UserMapper();

    private Connection connection;

    static final Logger LOG = LoggerFactory.getLogger(JdbcUserDao.class);

    public JdbcUserDao() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_DATABASE_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            LOG.trace("Constructor: ",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByName(String name) {
        User user = null;
        String sql = "SELECT * FROM user WHERE name = " + "'" + name + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            if (resultSet.next()) {
                user = USER_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            LOG.trace("getByName(): ",e);
            throw new RuntimeException(e);
        }
        return user;
    }
}
