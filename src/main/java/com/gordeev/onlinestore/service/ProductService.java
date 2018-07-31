package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;

import java.util.List;

public class ProductService {
    private int productsOnPage;
    private int productsQuantity;
    private int defaultPageNumber;

    private ProductDao productDao;

    private ExchangeRateService exchangeRateService;

    public ProductService() {
    }

    public List<Product> getAll(int limit, int from){
        List<Product> products = productDao.getAll(limit, from);

        if (exchangeRateService.getExchangeRate().isPresent()){
            double rate = exchangeRateService.getExchangeRate().get();
            for (Product product : products) {
                double currencyPrice = product.getPriceUah() / rate;
                product.setPriceUsd(currencyPrice);
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

    public int getProductsOnPage() {
        return productsOnPage;
    }

    public void setProductsOnPage(int productsOnPage) {
        this.productsOnPage = productsOnPage;
    }

    public int getDefaultPageNumber() {
        return defaultPageNumber;
    }

    public void setDefaultPageNumber(int defaultPageNumber) {
        this.defaultPageNumber = defaultPageNumber;
    }

    public int getProductsQuantity() {
        if (productsQuantity == 0){
            productsQuantity = productDao.getProductsQuantity();
        }
        return productsQuantity;
    }
}
