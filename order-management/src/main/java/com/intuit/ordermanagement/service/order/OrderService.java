package com.intuit.ordermanagement.service.order;

import java.util.List;
import java.util.UUID;

import com.intuit.ordermanagement.model.Order;

public interface OrderService {
	public Order createOrder(Order order);
	public Order getOrderDetailsByOrderId(UUID orderId);
	public List<Order> getAllOrders();
}
