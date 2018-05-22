package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDao userDao = (UserDao) ServiceLocator.getService("userDao");

    public UserService() {
    }

    public User getByName (String name) {return userDao.getByName(name);}

    public User autenticate(String name, String password){
        User user = getByName(name);

        if (user != null) {
            if (user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
