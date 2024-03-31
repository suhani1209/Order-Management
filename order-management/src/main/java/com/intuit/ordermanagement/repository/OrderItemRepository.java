package com.intuit.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intuit.ordermanagement.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
