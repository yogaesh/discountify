package com.discountify.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class DiscountLineItem {
	private double amount;
	private String description;
	
	public DiscountLineItem(){}
	public DiscountLineItem(String description, double amount){
		this.description = description;
		this.amount = amount;
	}
}
