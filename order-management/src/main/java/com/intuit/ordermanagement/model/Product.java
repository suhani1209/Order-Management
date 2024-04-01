package com.intuit.ordermanagement.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String productName;
	private BigDecimal price;
	private String description;
	
	public Product() {}
	
	public Product(String name, BigDecimal price, String description) {
		super();
		this.productName = name;
		this.price = price;
		this.description = description;
	}
	public Product(Long id, String name, BigDecimal price, String description) {
		super();
		this.id = id;
		this.productName = name;
		this.price = price;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
