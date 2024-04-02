package com.intuit.ordermanagement.service.product;

import java.util.List;

import com.intuit.ordermanagement.model.Product;

public interface ProductService {
	public List<Product> getAllProducts();
	public Product getProductById(Long productId);
	public Product createProduct(Product product);
}
