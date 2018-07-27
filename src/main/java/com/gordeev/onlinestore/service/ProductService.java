package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.entity.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private ExchangeRateService exchangeRateService;
    private int productsOnPage;
    private int productsQuantity;
    private int DEFAULT_PAGE_NUMBER;

    public ProductService() {
    }

    public List<Product> getAll(int limit, int from){
        List<Product> products = productDao.getAll(limit, from);

        if (exchangeRateService.getExchangeRate().isPresent()){
            double rate = exchangeRateService.getExchangeRate().get();
            for (Product product : products) {
                double currencyPrice = product.getPrice() / rate;
                product.setCurrencyPrice(currencyPrice);
            }
        }

        return products;
    }

    public void add(Product product){
        productDao.add(product);
        productsQuantity++; //TODO: check for adding from DB - status from productDao.delete(product) == -1  ???
    }

    public void edit(Product product){
        productDao.edit(product);
    }

    public Product getById(int id) {
        return productDao.getById(id);
    }

    public void delete(Product product) {
        productDao.delete(product);
        productsQuantity--; //TODO: check for deleting from DB - status from productDao.delete(product) == -1  ???
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
        return DEFAULT_PAGE_NUMBER;
    }

    public void setDEFAULT_PAGE_NUMBER(int DEFAULT_PAGE_NUMBER) {
        this.DEFAULT_PAGE_NUMBER = DEFAULT_PAGE_NUMBER;
    }

    public int getProductsQuantity() {
        if (productsQuantity == 0){
            productsQuantity = productDao.getProductsQuatity();
        }
        return productsQuantity;
    }
}
