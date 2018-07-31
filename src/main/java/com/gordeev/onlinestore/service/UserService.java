package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.UserDao;
import com.gordeev.onlinestore.entity.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private UserDao userDao;

    public UserService() {
    }

    private User getByName(String name) {return userDao.getByName(name);}

    public User autenticate(String name, String password) throws SecurityException {
        User user = getByName(name);

        if (user != null) {
            String encryptedPassword = encrypt(password + user.getSalt());

            if (user.getPassword().equals(encryptedPassword)){
                return user;
            }
        }
        throw new SecurityException("No such user found: " + name);
    }

    public String encrypt(String text) {
        StringBuilder encryptedPassword = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte bytes[] = text.getBytes();
        byte digest[] = new byte[0];
        if (messageDigest != null) {
            digest = messageDigest.digest(bytes);
        }
        for (byte aDigest : digest) {
            encryptedPassword.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
        }

        return encryptedPassword.toString();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
