package com.intuit.ordermanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.ordermanagement.dto.ItemQuantityPair;
import com.intuit.ordermanagement.dto.OrderPayload;
import com.intuit.ordermanagement.exception.ProductNotFoundException;
import com.intuit.ordermanagement.model.Order;
import com.intuit.ordermanagement.model.OrderItem;
import com.intuit.ordermanagement.model.Product;
import com.intuit.ordermanagement.service.order.OrderService;
import com.intuit.ordermanagement.service.product.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @Test
    public void testPlaceOrder() throws Exception {
        // Mock product data
        long productId = 1L;
        BigDecimal price = BigDecimal.valueOf(10.0);
        Product product = new Product(productId, "test product", price, "product desc");

        // Mock ProductService to return the mock product
        when(productService.getProductById(anyLong())).thenReturn(product);

        UUID orderId = UUID.randomUUID();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(orderId, productId, 1, price));
        Order order = new Order(orderId, orderItems, price, "CREATED");
        
        // Mocking orderService to return order
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        OrderPayload payload = new OrderPayload();
        // Set payload items as per your test scenario
        List<ItemQuantityPair> items = new ArrayList<>();
        items.add(new ItemQuantityPair(productId, 1)); // Add an item to the payload
        payload.setItems(items);

        // Making POST request to /api/order
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
    
    @Test
    public void testPlaceOrderProductNotFound() throws Exception {
        // Mock ProductService to throw ProductNotFoundException
        long nonExistingProductId = 999L;
        when(productService.getProductById(eq(nonExistingProductId)))
                .thenThrow(new ProductNotFoundException(nonExistingProductId));

        OrderPayload payload = new OrderPayload();
        // Set payload items with a product that does not exist
        List<ItemQuantityPair> items = Collections.singletonList(new ItemQuantityPair(nonExistingProductId, 1));
        payload.setItems(items);

        // Making POST request to /api/order
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Product not found with ID: " + nonExistingProductId));
    }
}
