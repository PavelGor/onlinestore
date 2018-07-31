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
    private static final String GET_USER_BY_NAME_SQL = "SELECT * FROM users WHERE name = ?";

    private DataSource dataSource;

    @Override
    public User getByName(String name) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_USER_BY_NAME_SQL)){
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserMapper userMapper = new UserMapper();
                    user = userMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format("Cannot execute query: %s", GET_USER_BY_NAME_SQL), e);
            throw new RuntimeException(e);
        }
        return user;
    }

    public void setDataSource(DataSource dataSource) { //TODO check without it, when take Spring factorypostprocessor
        this.dataSource = dataSource;
    }
}
