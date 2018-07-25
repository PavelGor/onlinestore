package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.ProductMapper;
import com.gordeev.onlinestore.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class JdbcProductDao implements ProductDao{
    private static final Logger LOG = LoggerFactory.getLogger(JdbcProductDao.class);

    private static final String ADD_PRODUCT_SQL = "INSERT INTO product (name, price, description, img_link) VALUES ( ?, ?, ?, ?)";
    private static final String GET_ALL_SQL = "SELECT * FROM product LIMIT ? OFFSET ?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM product WHERE id = ?";
    private static final String EDIT_PRODUCT_SQL = "UPDATE product SET name = ?, price = ?, description = ?, img_link = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_SQL ="DELETE FROM product WHERE id= ?;";

    private DataSource dataSource;
    private ProductMapper productMapper = new ProductMapper();

    @Override
    public List<Product> getAll(int limit, int from) {
        List<Product> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_SQL)){
            statement.setInt(1, limit);
            statement.setInt(2, from);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(productMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Cannot execute query: {}", GET_ALL_SQL, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_SQL)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getImgLink());
            statement.executeUpdate();
            LOG.info("Product added to DB: " + product.toString());
        } catch (SQLException e) {
            LOG.error("Cannot execute query: {}", ADD_PRODUCT_SQL, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        Product product = new Product();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_SQL)
             ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = productMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Cannot execute query: {}", GET_BY_ID_SQL, e);
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void edit(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EDIT_PRODUCT_SQL)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getImgLink());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
            LOG.info("Product edited in DB: " + product.toString());
        } catch (SQLException e) {
            LOG.error("Cannot execute query: {}", EDIT_PRODUCT_SQL, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)){
            statement.setInt(1, product.getId());
            statement.executeUpdate();
            LOG.info("Product deleted from DB: " + product.toString());
        } catch (SQLException e) {
            LOG.error("Cannot execute query: {}", DELETE_PRODUCT_SQL, e);
            throw new RuntimeException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
