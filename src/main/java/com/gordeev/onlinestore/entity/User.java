package com.gordeev.onlinestore.entity;

public class User {
    private String userName;
    private String password;
    private String sole;
    private UserRole role;

    public User(String userName, String password, String sole, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.sole = sole;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

