package com.discountify.discounts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.DiscountService;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Discount {
	private long id;
	private String name;
	private String description;
	private Discount nextIfDiscountApplied;
	private Discount nextIfDiscountNotApplied;
	private String discountType;
	private double discountValue;
	private int purchaseUnits;
	@Autowired
	protected UserService userService;
	@Autowired
	protected DiscountService discountService;
	@Autowired
	protected UtilService utilService;
	
	protected abstract boolean checkApplicability(Order order);
	public double getDiscountAmount(Order order){
		if(order == null){
			return 0;
		}
		
		List<Item> items = order.getItems();
		if(items == null || items.isEmpty()){
			return 0;
		}
		
		if(discountType.equals("absolute")){
			double total = order.getTotalAmount();
			if(total == 0.0){
				total = discountService.getSubtotalExcludingCategories(items, null);
			}
			return purchaseUnits == 0 ? discountValue : ((int)(total/purchaseUnits)) * discountValue;
		}
		
		List<ItemCategory> categories = new ArrayList<>();
		categories.add(ItemCategory.GROCERY);
		
		return discountValue * discountService.getSubtotalExcludingCategories(items, categories);
	}
}