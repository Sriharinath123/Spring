package com.microservices.demo.DTO;

import java.util.List;

public class OrderRequest 
{
	private List<OrderLineItemsDto> orderLineItemsDtoList;

	public List<OrderLineItemsDto> getOrderLineItemsDtoList() {
		return orderLineItemsDtoList;
	}

	public void setOrderLineItemsDtoList(List<OrderLineItemsDto> orderLineItemsDtoList) {
		this.orderLineItemsDtoList = orderLineItemsDtoList;
	}

	public OrderRequest(List<OrderLineItemsDto> orderLineItemsDtoList) {
		super();
		this.orderLineItemsDtoList = orderLineItemsDtoList;
	}

	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
     
}
