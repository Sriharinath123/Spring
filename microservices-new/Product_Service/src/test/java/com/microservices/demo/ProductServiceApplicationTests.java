package com.microservices.demo;

import com.microservices.demo.controller.ProductController;
import com.microservices.demo.service.ProductService;
import com.microservices.demo.DTO.ProductRequest;
import com.microservices.demo.DTO.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setDescription("Test Description");
        request.setPrice(new BigDecimal("10.99"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Product\",\"description\":\"Test Description\",\"price\":10.99}")
        )
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(productService, times(1)).createProduct(request);
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Dummy data for product response
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1, "Product 1", "Description 1", new BigDecimal("20.99")),
                new ProductResponse(2, "Product 2", "Description 2", new BigDecimal("30.99"))
        );

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/getAllProducts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(products.size())); // Check if response contains correct number of products

        verify(productService, times(1)).getAllProducts();
    }
}
