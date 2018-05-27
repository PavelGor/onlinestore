package com.gordeev.onlinestore.dao.jdbc.mapper;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        UserRole role;
        if (resultSet.getString("role").equals(UserRole.ADMIN.toString())){
            role = UserRole.ADMIN;
        } else {
            role = UserRole.USER;
        }
        return new User(resultSet.getString("name"), resultSet.getString("password"), role);
    }
}
