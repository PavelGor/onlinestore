package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.locator.ServiceLocator;

import java.util.List;

public class ProductService {
    private ProductDao productDao =  ServiceLocator.getService(ProductDao.class);

    public ProductService() {
    }

    public List<Product> getAll(){
        return productDao.getAll();
    }

    public void add(Product product){
        productDao.add(product);
    }

    public void edit(Product product){
        productDao.edit(product);
    }

    public Product getById(int id) {
        return productDao.getById(id);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }
}
