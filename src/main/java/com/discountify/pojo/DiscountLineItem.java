package com.discountify.pojo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscountLineItem {
	private BigDecimal amount;
	private String description;
}
