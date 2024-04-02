package com.intuit.ordermanagement.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.intuit.ordermanagement.exception.OrderNotFoundException;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.service.order.OrderService;

@SpringBootTest
public class OrderServiceErrorTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testEmptyOrderCreation() {
        // Attempt to create an order with no items
        assertThrows(IllegalArgumentException.class, () -> {
            Order emptyOrder = new Order();
            orderService.createOrder(emptyOrder);
        });
    }

    @Test
    public void testInvalidOrderItem() {
        // Attempt to create an order with an invalid item
        assertThrows(IllegalArgumentException.class, () -> {
            Order invalidOrder = new Order();
            List<OrderItem> invalidItems = new ArrayList<>();
            invalidItems.add(new OrderItem(-1L, null, null, 0, BigDecimal.ZERO)); // Negative quantity and zero price
            orderService.createOrder(invalidOrder);
        });
    }

    @Test
    public void testOrderNotFound() {
        // Attempt to retrieve details of a non-existing order
        assertThrows(OrderNotFoundException.class, () -> {
            UUID nonExistingOrderId = UUID.randomUUID();
            orderService.getOrderDetailsByOrderId(nonExistingOrderId);
        });
    }
}
