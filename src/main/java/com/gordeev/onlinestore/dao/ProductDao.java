package com.gordeev.onlinestore.dao;

import com.gordeev.onlinestore.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    void add(Product product);
    Product getById(int id);
    void edit(Product product);
    void delete(Product product);
}
