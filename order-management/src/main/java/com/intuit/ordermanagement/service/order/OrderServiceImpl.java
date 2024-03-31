package com.intuit.ordermanagement.service.order;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.ordermanagement.exception.OrderNotFoundException;
import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	@Transactional
	public Order createOrder(Order order) {
		BigDecimal totalOrderValue = BigDecimal.ZERO;
		for(OrderItem item: order.getItems()) {
			totalOrderValue = totalOrderValue.add(item.calculateTotalValueOfOrderItem());
		}
		order.setTotalOrderValue(totalOrderValue);
	    return orderRepository.save(order);
	}

	@Override
	public Order getOrderDetails(UUID orderId) {
		return orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException(orderId));
	}

}