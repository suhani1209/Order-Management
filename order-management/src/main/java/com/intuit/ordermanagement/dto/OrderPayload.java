package com.intuit.ordermanagement.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class OrderPayload {
	@NotEmpty(message = "Order Items list must not be empty")
    @Valid // Enable validation for elements of the list
	private List<ItemQuantityPair> items;
	
	public OrderPayload() {}
	public OrderPayload(List<ItemQuantityPair> items) {
		super();
		this.items = items;
	}

	public List<ItemQuantityPair> getItems() {
		return items;
	}

	public void setItems(List<ItemQuantityPair> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "OrderPayload [items=" + items + "]";
	}
	
}
