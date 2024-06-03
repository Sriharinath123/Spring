package com.microservices.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.demo.DTO.InventoryResponse;
import com.microservices.demo.DTO.InventoryResponse.Builder;
import com.microservices.demo.model.Inventory;
import com.microservices.demo.repo.InventoryRepository;

@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
    
    
    //Pathvariable:-  http://localhost:8082/api/inventory/IPhone-15
    
    //Pathvariable:-  http://localhost:8082/api/inventory/IPhone-15, Iphone-13, Realme-12
     
    //RequestParam:-  http://localhost:8082/api/inventory?skuCode=IPhone-15&skuCode=Iphone-13&skuCode= Realme-12


    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        logger.info("Received SKU codes: {}", skuCodes);
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory -> {
                    Builder builder = InventoryResponse.builder();
                    return builder
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build();
                })
                .collect(Collectors.toList());
    }
}