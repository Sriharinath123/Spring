package com.microservices.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.demo.DAO.OrderRepository;
import com.microservices.demo.DTO.OrderLineItemsDto;
import com.microservices.demo.DTO.OrderRequest;
import com.microservices.demo.model.Order;
import com.microservices.demo.model.OrderLineItems;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    // Placing the order
    public boolean placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        // Creating 
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(dto -> mapToDto(dto, order))
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        // Constructing the URL string with SKU codes as query parameters
        String inventoryServiceUrl = "http://InventoryService/api/inventory?" +
                skuCodes.stream()
                        .map(skuCode -> "skuCode=" + skuCode)
                        .collect(Collectors.joining("&"));
        System.out.println("Calling Inventory Service URL: " + inventoryServiceUrl);

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri(inventoryServiceUrl)
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        for (InventoryResponse response : inventoryResponseArray) {
            String skuCode = response.getSkuCode();
            boolean inStock = response.isInStock();
            System.out.println("SKU Code: " + skuCode + ", In Stock: " + inStock);

            // Log the inventory response objects
            logger.info("Inventory Response: {}", response);
        }

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            System.out.println("Order placed successfully.");
            return true;
        } else {
            System.out.println("Some products are out of stock. Order not placed.");
            return false;
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto, Order order) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setOrder(order);

        return orderLineItems;
    }
}
