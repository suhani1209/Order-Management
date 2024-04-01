package com.intuit.ordermanagement.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	private UUID id;
	
	@OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();
	
	private BigDecimal totalOrderValue;
	private String orderstatus;
	
	public Order() {}

	public Order(UUID id) {
		this.id = id;
		this.orderstatus = "CREATED";
	}

	public Order(UUID id, List<OrderItem> items, BigDecimal totalOrderValue, String orderstatus) {
		this(id, items, orderstatus);
		this.totalOrderValue = totalOrderValue;
		
	}
	public Order(UUID id, List<OrderItem> items, String orderstatus) {
		this.id = id;
		this.items = items;
		this.orderstatus = orderstatus;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public BigDecimal getTotalOrderValue() {
		return totalOrderValue;
	}

	public void setTotalOrderValue(BigDecimal totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", items=" + items + ", totalOrderValue=" + totalOrderValue + ", orderstatus="
				+ orderstatus + "]";
	}
	
}
