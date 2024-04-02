package com.intuit.ordermanagement.dto;

import jakarta.validation.constraints.Positive;

public class ItemQuantityPair {
	@Positive(message = "Product ID must be positive")
	private Long productId;
	
	@Positive(message = "Quantity must be positive")
	private int quantity;
	
	public ItemQuantityPair() {}
	
	public ItemQuantityPair(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ItemQuantityPair [productId=" + productId + ", quantity=" + quantity + "]";
	}
}
