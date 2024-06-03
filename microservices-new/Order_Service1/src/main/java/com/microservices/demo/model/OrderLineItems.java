package com.microservices.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderLineItems 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String skuCode;
      private BigDecimal price;
      private Integer quantity;
      
      @ManyToOne
      @JoinColumn(name = "order_id") // Specify the foreign key column
      private Order order;
      
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public OrderLineItems(Long id, String skuCode, BigDecimal price, Integer quantity, Order order) {
		super();
		this.id = id;
		this.skuCode = skuCode;
		this.price = price;
		this.quantity = quantity;
		this.order = order;
	}
	public OrderLineItems() {
		super();
	}
      
      
      
}
