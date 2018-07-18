package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.UserMapper;
import com.gordeev.onlinestore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String GET_USER_BY_NAME_SQL = "SELECT * FROM users WHERE name = ";

    private DataSource dataSource;

    @Override
    public User getByName(String name) {
        User user = null;
        String sql =  GET_USER_BY_NAME_SQL + "'" + name + "'";
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            if (resultSet.next()) {
                user = UserMapper.mapRow(resultSet);//TODO: check
            }
        } catch (SQLException e) {
            LOG.error("getByName(): ",e);
            throw new RuntimeException(e);
        }
        return user;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
