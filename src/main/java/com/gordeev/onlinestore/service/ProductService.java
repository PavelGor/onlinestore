package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.ProductGroup;
import com.gordeev.onlinestore.locator.ServiceLocator;

import java.util.List;

public class ProductService {
    private ProductDao productDao = (ProductDao) ServiceLocator.getService("productDao");
    private final static ProductService instance = new ProductService();

    public ProductService() {
    }

    public static ProductService getInstance() {
        return instance;
    }

    public List<Product> getAll(){
        return productDao.getAll();
    }

    public List<Product> getByGroup(ProductGroup productGroup){
        return productDao.getByGroup(productGroup);
    }

    public void add(Product product){
        productDao.add(product);
    }

}
