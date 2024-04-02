package com.intuit.ordermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.product.ProductService;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	@Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(100), "Test Description");

        // When
        productService.createProduct(product);

        // Then
        Product retrievedProduct = productService.getProductById(product.getId());
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getPrice(), retrievedProduct.getPrice());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
    }

    @Test
    public void testGetProductById() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(100), "Test Description");
        productService.createProduct(product);

        // When
        Product retrievedProduct = productService.getProductById(product.getId());

        // Then
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getPrice(), retrievedProduct.getPrice());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Given a non-existing product ID
        long nonExistingProductId = 999L;

        // When retrieving the product
        // Then ProductNotFoundException should be thrown
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(nonExistingProductId));
    }

    @Test
    public void testGetAllProducts() {
        // Given
        Product product1 = new Product("Test Product 1", BigDecimal.valueOf(100), "Test Description 1");
        Product product2 = new Product("Test Product 2", BigDecimal.valueOf(200), "Test Description 2");
        productService.createProduct(product1);
        productService.createProduct(product2);

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertEquals(2, products.size());
    }
}
