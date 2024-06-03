package com.microservices.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.demo.DAO.ProductRepository;
import com.microservices.demo.DTO.ProductRequest;
import com.microservices.demo.DTO.ProductResponse;
import com.microservices.demo.model.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        
        Product savedProduct = productRepository.save(product);
        
        logger.info("Product {} is saved", savedProduct.getId()); 
    }
    
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    
    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        return response;
    }
}
