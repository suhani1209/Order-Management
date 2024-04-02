package com.intuit.ordermanagement.service.orderItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.repository.OrderItemRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	@Transactional
	public OrderItem createOrderItem(OrderItem orderItem) {
		logger.info("Creating order item: {}", orderItem);
		return orderItemRepository.save(orderItem);
	}

}
