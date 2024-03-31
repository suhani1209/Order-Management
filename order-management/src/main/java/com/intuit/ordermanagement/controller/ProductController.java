package com.intuit.ordermanagement.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/{productId}/price")
	public BigDecimal getProductPrice(@PathVariable Long productId) {
		return productRepository.findById(productId).map(Product::getPrice).orElseThrow(()-> new ProductNotFoundException(productId));
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
}
