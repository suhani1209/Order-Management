package com.intuit.ordermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.ordermanagement.exception.OrderNotFoundException;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.service.order.OrderService;
import com.intuit.ordermanagement.service.product.ProductService;

@SpringBootTest
@Transactional
public class OrderServiceIT {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testCreateOrder() {
		// Test data
		UUID orderId = UUID.randomUUID();
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(orderId, 1L, 2, BigDecimal.valueOf(50)));
        
        Order savedOrder = new Order(orderId, items, "CREATED");
        
        //saving to db
        savedOrder = orderService.createOrder(savedOrder);
        
        // getting order details
        Order retrievedOrder = orderService.getOrderDetailsByOrderId(orderId);
        
        assertNotNull(retrievedOrder);
        assertEquals(savedOrder.getItems().size(), retrievedOrder.getItems().size());
        assertEquals(savedOrder.getTotalOrderValue(), retrievedOrder.getTotalOrderValue());
        assertEquals(savedOrder.getOrderstatus(), retrievedOrder.getOrderstatus());
	}
	
	@Test
    public void testGetOrderDetailsByOrderIdWhenOrderNotFound() {
        // Given a non-existing order ID
        UUID nonExistingOrderId = UUID.randomUUID();

        // When retrieving the order
        // Then OrderNotFoundException should be thrown
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderDetailsByOrderId(nonExistingOrderId));
    }
	
	@Test
	public void testGetAllOrders() {
		// creating test data
		List<Order> orders = createTestOrderObjects();
		
		//pushing test orders in db
		for(Order order: orders) {
			orderService.createOrder(order);
		}
		//retrieving all the orders
		List<Order> retrievedOrders = orderService.getAllOrders();
		
		assertEquals(2, retrievedOrders.size());
	}

	// create test data
	private List<Order> createTestOrderObjects() {
		UUID orderId1 = UUID.randomUUID();
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(orderId1, 1L, 2, BigDecimal.valueOf(50)));
        Order order1 = new Order(orderId1, items, "CREATED");
        
        UUID orderId2 = UUID.randomUUID();
        List<OrderItem> items2 = new ArrayList<>();
        items.add(new OrderItem(orderId2, 1L, 2, BigDecimal.valueOf(50)));
        Order order2 = new Order(orderId2, items2, "CREATED");
        
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        
		return orders;
	}
}