package com.gordeev.onlinestore.dao;

import com.gordeev.onlinestore.entity.User;

public interface UserDao {
    User getByName(String name);
}
