package com.intuit.ordermanagement.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.product.ProductService;

public class OrderDtoUtil {
	public static Order createOrderFromPayload(OrderPayload payload, UUID orderId, ProductService productService) {
        Order order = new Order(orderId);
        List<OrderItem> orderItems = payload.getItems().stream()
                .map(item -> createOrderItem(item, orderId, productService))
                .collect(Collectors.toList());

        order.setItems(orderItems);

        return order;
    }

    private static OrderItem createOrderItem(ItemQuantityPair item, UUID orderId, ProductService productService) {
        Product product = productService.getProductById(item.getProductId());
        return new OrderItem(orderId, item.getProductId(), item.getQuantity(), product.getPrice());
    }
}
