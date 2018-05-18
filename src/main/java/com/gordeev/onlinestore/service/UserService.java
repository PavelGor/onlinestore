package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        //users.add(new User("user","user"));
        users.add(new User("admin","admin"));
    }

    public User autenticate(String name, String password){
        for (User user : users) {
            if (user.getUsername().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }
}
