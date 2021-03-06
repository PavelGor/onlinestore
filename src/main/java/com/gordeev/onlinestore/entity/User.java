package com.gordeev.onlinestore.entity;

import java.util.Objects;

public class User {
    private String userName;
    private String password;
    private String salt;
    private UserRole role;

    public User(String userName, String password, String salt, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password);
    }
}

