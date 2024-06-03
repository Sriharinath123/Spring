package com.microservices.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.demo.DAO.OrderRepository;
import com.microservices.demo.DTO.InventoryResponse;
import com.microservices.demo.DTO.OrderLineItemsDto;
import com.microservices.demo.DTO.OrderRequest;
import com.microservices.demo.model.Order;
import com.microservices.demo.model.OrderLineItems;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

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
        String inventoryServiceUrl = "http://localhost:8087/api/inventory?" +
                skuCodes.stream()
                        .map(skuCode -> "skuCode=" + skuCode)
                        .collect(Collectors.joining("&"));
        System.out.println("Calling Inventory Service URL: " + inventoryServiceUrl);

        try {
            InventoryResponse[] inventoryResponseArray = webClient.get()
                    .uri(inventoryServiceUrl)
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            if (inventoryResponseArray != null) {
                List<InventoryResponse> inventoryResponses = Arrays.asList(inventoryResponseArray);

                for (InventoryResponse response : inventoryResponses) {
                    String skuCode = response.getSkuCode();
                    boolean inStock = response.isInStock();
                    System.out.println("SKU Code: " + skuCode + ", In Stock: " + inStock);
                        
                    // Log the inventory response objects
                    System.out.println("Inventory Response: " + response);
                }

                boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);

                System.out.println("All Products In Stock: " + allProductsInStock);

                if (allProductsInStock) {
                    orderRepository.save(order);
                    System.out.println("Order placed successfully.");
                } else {
                    System.out.println("Some products are out of stock. Order not placed.");
                }
            } else {
                System.out.println("No response received from inventory service.");
            }
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
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
