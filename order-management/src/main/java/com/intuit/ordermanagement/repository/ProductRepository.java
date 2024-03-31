package com.intuit.ordermanagement.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intuit.ordermanagement.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
