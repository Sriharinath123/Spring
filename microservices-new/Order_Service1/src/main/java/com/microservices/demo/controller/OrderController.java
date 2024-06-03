package com.microservices.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.DTO.OrderRequest;
import com.microservices.demo.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "InventoryService", fallbackMethod = "fallbackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Placing order for: {}", orderRequest);
        boolean orderPlaced = orderService.placeOrder(orderRequest);
        if (orderPlaced) {
            logger.info("Order placed successfully");
            return "Order Placed Successfully";
        } else {
            logger.warn("Order not placed due to out of stock products");
            return "Some products are out of stock. Order not placed.";
        }
    }
    
    

    public String fallbackMethod(OrderRequest orderRequest, Throwable throwable) {
        logger.error("Fallback method invoked due to: {}", throwable.getMessage(), throwable);
        return "Oops! Something went wrong. Please, order after some time.";
    }
}
