package com.intuit.ordermanagement.service.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> getAllProducts() {
		logger.info("Fetching all products");
		return productRepository.findAll();
	}

	@Override
	@Cacheable(key = "#productId")
	public Product getProductById(Long productId) {
		logger.info("Fetching product by ID: {}", productId);
		return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
	}

	@Override
	@Transactional
	public Product createProduct(Product product) {
		logger.info("Creating product: {}", product);
		return productRepository.save(product);
	}

}
