package com.discountify.pojo;

import com.discountify.item.categories.ItemCategory;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {
	private long id;
	private String description;
	private ItemCategory category;
	private double price;
}
