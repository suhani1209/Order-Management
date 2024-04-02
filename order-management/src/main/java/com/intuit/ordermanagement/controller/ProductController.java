package com.intuit.ordermanagement.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.product.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/{productId}/price")
	@Cacheable(value = "productPrices", key = "#productId")
	public BigDecimal getProductPrice(@PathVariable Long productId) {
		logger.info("Fetching price for product with ID: {}", productId);
		
		Product product = productService.getProductById(productId);
		
	    logger.info("Price fetched successfully for product with ID: {}", productId);
		return product.getPrice();
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		logger.info("Fetching all products");
		
        List<Product> products = productService.getAllProducts();
        
        logger.info("Fetched {} products", products.size());
        return products;
	}
}
