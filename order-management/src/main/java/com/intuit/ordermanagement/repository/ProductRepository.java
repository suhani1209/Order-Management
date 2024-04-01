package com.intuit.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.ordermanagement.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
