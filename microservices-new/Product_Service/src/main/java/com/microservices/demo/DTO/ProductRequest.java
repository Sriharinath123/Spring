package com.microservices.demo.DTO;

import java.math.BigDecimal;

public class ProductRequest 
{
	
	   public String name;
	   public String description;
	   public BigDecimal price;
	
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
	public ProductRequest(String name, String description, BigDecimal price) {
		super();
		
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public ProductRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	   
	
}
