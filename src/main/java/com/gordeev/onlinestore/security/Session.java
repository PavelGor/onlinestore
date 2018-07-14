package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {

    private User user;
    private LocalDateTime expireTime;
    private String token;
    private List<Product> cart = new ArrayList<>();

    Session(String token) {
        this.token = token;
    }

    public Session() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(token, session.token);
    }

    @Override
    public int hashCode() {

        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", expireTime=" + expireTime +
                ", token='" + token + '\'' +
                ", cart=" + cart +
                '}';
    }
}
