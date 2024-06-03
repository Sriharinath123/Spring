package com.microservices.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{

}
