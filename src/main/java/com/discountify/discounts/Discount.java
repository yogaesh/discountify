package com.discountify.discounts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.DiscountService;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Component
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
	protected UtilService utilService;
	@Autowired
	protected DiscountService discountService;
	
	protected abstract boolean checkApplicability(Order order);
	protected double getDiscountAmount(Order order){
		if(order == null){
			return 0;
		}
		
		List<Item> items = order.getItems();
		if(items == null || items.isEmpty()){
			return 0;
		}
		
		double total = order.getTotalAmount();
		if(total < 0){
			return 0;
		}

		double currentDiscount = order.getDiscounts();
		double netDiscount = 0;
		
		if(discountType.equals("absolute")){
			if(total == 0){
				total = utilService.getSubtotalExcludingCategories(items, null);
			}
			
			netDiscount = purchaseUnits == 0 ? discountValue : ((int)((total-currentDiscount)/purchaseUnits)) * discountValue;
			return formatCurrency(netDiscount);
		}
		
		List<ItemCategory> categories = new ArrayList<>();
		categories.add(ItemCategory.GROCERY);
		netDiscount = discountValue * utilService.getSubtotalExcludingCategories(items, categories);
		
		return formatCurrency(netDiscount);
	}
	
	public Order applyDiscount(Order order){
		if(checkApplicability(order)){
			double currentDiscountAmount = order.getDiscounts();
			order.setDiscounts(currentDiscountAmount + getDiscountAmount(order));
			if(nextIfDiscountApplied != null){
				order = nextIfDiscountApplied.applyDiscount(order);
			}
		}
		else if(nextIfDiscountNotApplied != null){
			order = nextIfDiscountNotApplied.applyDiscount(order);
		}
		return order; 
	}
	
	protected double formatCurrency(double value){
		 DecimalFormat currencyFormat = new DecimalFormat("#.00");
		 return Double.valueOf(currencyFormat.format(value));
	}
}