package com.gordeev.onlinestore.dao.jdbc.mapper;

import com.gordeev.onlinestore.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        product.setImgLink(resultSet.getString("img_link"));

        return product;
    }
}
