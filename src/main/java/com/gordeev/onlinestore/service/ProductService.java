package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao;

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

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
