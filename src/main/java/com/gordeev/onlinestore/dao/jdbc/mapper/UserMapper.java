package com.gordeev.onlinestore.dao.jdbc.mapper;

import com.gordeev.onlinestore.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {

        return new User(resultSet.getString("name"), resultSet.getString("password"));

    }
}
