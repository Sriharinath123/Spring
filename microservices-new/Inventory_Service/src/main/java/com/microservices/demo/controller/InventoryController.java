package com.microservices.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.DTO.InventoryResponse;
import com.microservices.demo.model.Inventory;
import com.microservices.demo.service.InventoryService;


@RestController
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/api/inventory")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.saveInventory(inventory);
        return ResponseEntity.ok(createdInventory);
    }

    @GetMapping("/api/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCodes) {
        logger.info("Received SKU codes: {}", skuCodes);
        List<InventoryResponse> inventoryResponses = inventoryService.isInStock(skuCodes);
        // Log each inventory response with its data
        for (InventoryResponse response : inventoryResponses) {
        	  logger.info("Inventory Response: {}", response);
            logger.info("SKU Code: {}, In Stock: {}", response.getSkuCode(), response.isInStock());
        }
        return inventoryResponses;
    }

}
