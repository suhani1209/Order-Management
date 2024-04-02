package com.intuit.ordermanagement.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.product.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetProductPrice() throws Exception {
        // Mocking productService to return a product with a specific price
        long productId = 1L;
        BigDecimal price = BigDecimal.valueOf(10.0);
        Product product = new Product();
        product.setId(productId);
        product.setPrice(price);
        when(productService.getProductById(productId)).thenReturn(product);

        // Making GET request to /api/products/{productId}/price
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}/price", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(price.toString()));
    }
    
    @Test
    public void testGetProductPriceProductNotFound() throws Exception{
    	// Mock ProductService to throw ProductNotFoundException
    	long nonExistingProductId = 999L;
        when(productService.getProductById(eq(nonExistingProductId)))
                .thenThrow(new ProductNotFoundException(nonExistingProductId));
        
        // Making GET request to /api/products/{productId}/price
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}/price", nonExistingProductId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Product not found with ID: " + nonExistingProductId));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Mocking productService to return a list of products
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Product 1", BigDecimal.valueOf(10.0), "Description 1"));
        productList.add(new Product(2L, "Product 2", BigDecimal.valueOf(20.0), "Description 2"));
        when(productService.getAllProducts()).thenReturn(productList);

        // Making GET request to /api/products
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(productList.size()));
    }
}

