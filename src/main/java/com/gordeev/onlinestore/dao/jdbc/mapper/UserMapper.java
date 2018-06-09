package com.gordeev.onlinestore.dao.jdbc.mapper;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        String userRoleStr = resultSet.getString("role");
        UserRole role = UserRole.getByName(userRoleStr);

        return new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("sole"),role);
    }
}
