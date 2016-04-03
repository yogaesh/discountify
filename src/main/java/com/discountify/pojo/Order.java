package com.discountify.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
	private List<Item> items;
	private int userid;
	private double discounts;
	private double totalAmount;
}
