package com.intuit.ordermanagement.service.orderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

}
