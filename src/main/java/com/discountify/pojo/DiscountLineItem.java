package com.discountify.pojo;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class DiscountLineItem {
	private BigDecimal amount;
	private String description;
	
	public DiscountLineItem(){}
	public DiscountLineItem(String description, BigDecimal amount){
		this.description = description;
		this.amount = amount;
	}
}
