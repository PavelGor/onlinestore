package com.gordeev.onlinestore.dao;

import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.ProductGroup;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    List<Product> getByGroup(ProductGroup productGroup);
    void add(Product product);
    Product getById(int id);
    void update(Product product);
}
