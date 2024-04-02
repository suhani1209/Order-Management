package com.intuit.ordermanagement.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.ordermanagement.dto.ItemQuantityPair;
import com.intuit.ordermanagement.dto.OrderDtoUtil;
import com.intuit.ordermanagement.dto.OrderPayload;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.order.OrderService;
import com.intuit.ordermanagement.service.product.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	 public Order placeOrder(@Valid @RequestBody OrderPayload payload, BindingResult result) throws MethodArgumentNotValidException{
		logger.info("Received request to place order with payload: {}", payload);
		
		if (result.hasErrors()) {
			logger.error("Validation errors occurred while processing the order payload: {}", result.getAllErrors());
			try {
		        throw new MethodArgumentNotValidException(new MethodParameter(OrderController.class.getDeclaredMethod("placeOrder", OrderPayload.class,BindingResult.class), 0), result);
		    } catch (NoSuchMethodException e) {
		        throw new RuntimeException("Critical failure: Missing method 'placeOrder' in OrderController.", e);
		    }
	    }
		logger.debug("Creating order from payload");
		UUID orderId = UUID.randomUUID();
		Order order = new Order(orderId);
		//establish relationship between order item and new order & add totalOrderValue		
	    order = OrderDtoUtil.createOrderFromPayload(payload, orderId, productService);
	    
	    logger.info("Order created successfully: {}", order);
	    return orderService.createOrder(order);
    }
	
	private Order createOrderFromPayload(OrderPayload payload, UUID orderId, Order order) {
	    List<OrderItem> orderItems = payload.getItems().stream()
	            .map(item -> createOrderItem(item, orderId)) 
	            .collect(Collectors.toList());

	    order.setItems(orderItems);
	    
	    return order; 
    }
	
	private OrderItem createOrderItem(ItemQuantityPair item, UUID orderId) {
		Product product = productService.getProductById(item.getProductId());
		
		OrderItem orderItem = new OrderItem(orderId, item.getProductId(), item.getQuantity(), product.getPrice());
        
        return orderItem;
    }
}
