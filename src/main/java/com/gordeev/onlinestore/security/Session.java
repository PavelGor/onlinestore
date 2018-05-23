package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {

    private User user;
    private LocalDateTime expiredTime;
    private String token;
    private List<Product> cart = new ArrayList<>(); // кол-во продуктов в корзине не учтено!!!

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

    public List<Product> getCart() {
        return cart;
    }

    public void addToCart(Product product){
        cart.add(product);
    }

    public void removeFromCard (Product product){
        cart.remove(product);
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
