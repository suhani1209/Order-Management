package com.intuit.ordermanagement.controller;

import java.math.BigDecimal;
import java.util.List;

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
	@Autowired
	private ProductService productService;
	
	@GetMapping("/{productId}/price")
	@Cacheable(value = "productPrices", key = "#productId")
	public BigDecimal getProductPrice(@PathVariable Long productId) {
		return productService.getProductById(productId).getPrice();
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
}
