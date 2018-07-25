package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private ExchangeRateService exchangeRateService;

    public ProductService() {
    }

    public List<Product> getAll(int limit, int from){
        List<Product> products = productDao.getAll(limit, from);
        double rate;
        if (exchangeRateService.getExchangeRate().isPresent()){
            rate = exchangeRateService.getExchangeRate().get();
            for (Product product : products) {
                double currencyPrice = product.getPrice() / rate;
                product.setCurrencyPrice(currencyPrice);
            }
        }

        return products;
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

    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
}
