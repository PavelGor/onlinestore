package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.locator.ServiceLocator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {
    private UserDao userDao = (UserDao) ServiceLocator.getService(UserDao.class);

    public UserService() {
    }

    public User getByName (String name) {return userDao.getByName(name);}

    public User autenticate(String name, String password) throws IllegalAccessException {
        User user = getByName(name);

        if (user != null) {
            String encryptedPassword = encrypt(password + user.getSole()); //TODO: use UUID.randomUUID() when you will generate

            if (user.getPassword().equals(encryptedPassword)){
                return user;
            }
        }
        throw new IllegalAccessException("No such user found: " + name);
    }

    public String encrypt(String text) {
        StringBuilder code = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte bytes[] = text.getBytes();
        byte digest[] = messageDigest.digest(bytes);
        for (byte aDigest : digest) {
            code.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
        }

        return code.toString();
    }
}
