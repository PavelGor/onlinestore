package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.dao.ProductDao;
import com.gordeev.onlinestore.dao.jdbc.mapper.ProductMapper;
import com.gordeev.onlinestore.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gordeev.onlinestore.locator.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class JdbcProductDao implements ProductDao{
    private static final Logger LOG = LoggerFactory.getLogger(JdbcProductDao.class);

    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();

    private DataSource dataSource = (DataSource) ServiceLocator.getService(DataSource.class);

    @Override
    public List<Product> getAll() {
        List<Product> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product")){
            while (resultSet.next()) {
                result.add(PRODUCT_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            LOG.trace("getAll(): ",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO product (name, price, description, img_link) VALUES ( ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getImgLink());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.trace("add(): ",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        Product product = new Product();
        String sql = "SELECT * FROM product WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            if (resultSet.next()) {
                product = PRODUCT_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            LOG.trace("getById(): ",e);
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void edit(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, description = ?, img_link = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getImgLink());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.trace("edit(): ",e);
            throw new RuntimeException(e);
        }
    }

    //по хорошему удалять продукты из БД нельзя - история, необходимо ставить статус неактивен. Но похоже всё зависит от ТЗ
    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM product WHERE id= ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.trace("delete(): ",e);
            throw new RuntimeException(e);
        }
    }
}
