package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.ProductMapper;
import com.gordeev.onlinestore.entity.Product;
import com.gordeev.onlinestore.entity.ProductGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao{
    /**
     * JDBC Driver and database url
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_DATABASE_URL = "jdbc:mysql://localhost/onlinestore?useUnicode=true&characterEncoding=UTF8";
    /**
     * User and Password
     */
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();

    private Connection connection;

    public JdbcProductDao() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_DATABASE_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT productid, name, price, description, groupname, imglink FROM onlinestore.product inner join onlinestore.group on onlinestore.product.groupid=onlinestore.group.groupid")){
            while (resultSet.next()) {
                result.add(PRODUCT_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Product> getByGroup(ProductGroup productGroup) {
        return null;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO product (name, price, description, groupid, imglink) VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, 1);                             //допилить работу с группой продукта
            statement.setString(5, product.getImgLink());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public void update(Product product) {

    }
}
