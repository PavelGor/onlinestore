package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.UserMapper;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {

    static final Logger LOG = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final UserMapper USER_MAPPER = new UserMapper();

    private DataSource dataSource = (DataSource) ServiceLocator.getService("dataSource");

    @Override
    public User getByName(String name) {
        User user = null;
        String sql = "SELECT * FROM users WHERE name = " + "'" + name + "'";
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
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
