package com.gordeev.onlinestore.web.servlet;

import com.gordeev.onlinestore.entity.Product;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductServletTest {

    Product expectedProduct;
    HttpServletRequest request;
    AddProductServlet addProductServlet = new AddProductServlet();

    @Before
    public void setUp() throws Exception {

        expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setName("test_name");
        expectedProduct.setProductGroup("Сок");
        expectedProduct.setPrice(10);
        expectedProduct.setDescription("test_description");
        expectedProduct.setImgLink("test_imgLink");

        request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("test_name");
        when(request.getParameter("price")).thenReturn("10");
        when(request.getParameter("productGroup")).thenReturn("Сок");
        when(request.getParameter("description")).thenReturn("test_description");
        when(request.getParameter("imgLink")).thenReturn("test_imgLink");

    }

    @Test
    public void createProductFromRequestTest() {
        Product actualProduct = addProductServlet.createProductFromRequest(request);
        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getId(),actualProduct.getId());
        assertEquals(expectedProduct.getName(),actualProduct.getName());
        assertEquals(expectedProduct.getProductGroup(),actualProduct.getProductGroup());
        assertEquals(expectedProduct.getPrice(),actualProduct.getPrice(), 0.1);
        assertEquals(expectedProduct.getDescription(),actualProduct.getDescription());
    }
}