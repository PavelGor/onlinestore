package com.gordeev.onlinestore.dao.jdbc.mapper;

import com.gordeev.onlinestore.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductMapperTest {
    private Product expectedProduct;
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
        expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setName("test_name");
        expectedProduct.setPrice(10);
        expectedProduct.setDescription("test_description");
        expectedProduct.setImgLink("test_imgLink");

        resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("test_name");
        when(resultSet.getDouble("price")).thenReturn(10.0);
        when(resultSet.getString("description")).thenReturn("test_description");
        when(resultSet.getString("img_link")).thenReturn("test_imgLink");
    }

    @Test
    public void mapRowTest() throws SQLException {
        ProductMapper productMapper = new ProductMapper();
        Product actualProduct = productMapper.mapRow(resultSet);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getId(),actualProduct.getId());
        assertEquals(expectedProduct.getName(),actualProduct.getName());
        assertEquals(expectedProduct.getDescription(),actualProduct.getDescription());
        assertEquals(expectedProduct.getPrice(),actualProduct.getPrice(), 0.1);
        assertEquals(expectedProduct.getImgLink(),actualProduct.getImgLink());
    }
}