package com.microservices.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Product 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Integer id;
   public String name;
   public String description;
   public BigDecimal price;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public BigDecimal getPrice() {
	return price;
}
public void setPrice(BigDecimal price) {
	this.price = price;
}
public Product(Integer id, String name, String description, BigDecimal price) {
	super();
	this.id = id;
	this.name = name;
	this.description = description;
	this.price = price;
}
public Product() {
	super();
	// TODO Auto-generated constructor stub
}
   
}
