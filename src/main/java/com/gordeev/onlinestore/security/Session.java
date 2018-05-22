package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class Session {

    private User user;
    private LocalDateTime expiredTime;
    private String token;
    private List<Product> cart;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", expiredTime=" + expiredTime +
                ", token='" + token + '\'' +
                ", cart=" + cart +
                '}';
    }
}
