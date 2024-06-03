package com.microservices.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") // Specify the custom table name here
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String orderNumber;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Specify the mappedBy attribute to establish the relationship with OrderLineItems entity    
    private List<OrderLineItems> orderLineItemsList;
     
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public List<OrderLineItems> getOrderLineItemsList() {
		return orderLineItemsList;
	}
	public void setOrderLineItemsList(List<OrderLineItems> orderLineItemsList) {
		this.orderLineItemsList = orderLineItemsList;
	}
	
	
	public Order(Long id, String orderNumber, List<OrderLineItems> orderLineItemsList) 
	{
		super();
		this.id = id;
		this.orderNumber = orderNumber;
		this.orderLineItemsList = orderLineItemsList;
	}
	
	
	public Order() 
	{
		super();
	}
	
	
     
}
