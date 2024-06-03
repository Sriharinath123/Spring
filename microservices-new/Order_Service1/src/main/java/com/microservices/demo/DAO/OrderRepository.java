package com.microservices.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.demo.model.Order;



public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional custom methods can be added here if needed
}

