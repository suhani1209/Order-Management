package com.intuit.ordermanagement.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.intuit.ordermanagement.exception.OrderNotFoundException;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@CacheConfig(cacheNames = "orders")
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
	@Cacheable(key = "#orderId")
	public Order getOrderDetailsByOrderId(UUID orderId) {
		return orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException(orderId));
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}
