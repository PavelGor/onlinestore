package com.gordeev.onlinestore.web.servlet.utils;

import com.gordeev.onlinestore.entity.Product;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletUtilsTest {

    private Product expectedProduct;
    private HttpServletRequest request;

    @Before
    public void setUp() {

        expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setName("test_name");
        expectedProduct.setPrice(10);
        expectedProduct.setDescription("test_description");
        expectedProduct.setImgLink("test_imgLink");

        request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("test_name");
        when(request.getParameter("price")).thenReturn("10");
        when(request.getParameter("description")).thenReturn("test_description");
        when(request.getParameter("imgLink")).thenReturn("test_imgLink");

    }

    @Test
    public void createProductFromRequestTest() {
        Product actualProduct = ServletUtils.getProductFromRequest(request);
        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getId(),actualProduct.getId());
        assertEquals(expectedProduct.getName(),actualProduct.getName());
        assertEquals(expectedProduct.getPrice(),actualProduct.getPrice(), 0.1);
        assertEquals(expectedProduct.getDescription(),actualProduct.getDescription());
    }
}