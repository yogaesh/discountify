package com.discountify.pojo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
	private List<Item> items;
	private int userid;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private BigDecimal discounts = BigDecimal.ZERO;
	private BigDecimal amountAfterDiscount;
	private List<DiscountLineItem> discountDetails;
}
