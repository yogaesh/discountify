package com.discountify.discounts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.DiscountLineItem;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;
import lombok.Setter;

@Setter @Component
public abstract class Discount {
	@SuppressWarnings("unused")
	private long id;
	@SuppressWarnings("unused")
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
	
	protected abstract boolean checkApplicability(Optional<Order> order);
	protected double getDiscountAmount(Order order){
		
		List<Item> items = order.getItems();
		
		double total = order.getTotalAmount();
		double currentDiscount = order.getDiscounts();
		double netDiscount = 0;
		
		if(discountType.equals("absolute")){
			if(total == 0){
				total = utilService.getSubtotalExcludingCategories(items, new ArrayList<>());
			}
			
			netDiscount = purchaseUnits == 0 ? discountValue : ((int)((total-currentDiscount)/purchaseUnits)) * discountValue;
			return formatCurrency(netDiscount);
		}
		else{
			netDiscount = discountValue * utilService.getSubtotalExcludingCategories(items, Arrays.asList(ItemCategory.GROCERY));
			return formatCurrency(netDiscount);
		}
		
	}
	
	public Order applyDiscount(Optional<Order> orderStream){
		Order order = orderStream.get();
		if(checkApplicability(orderStream)){
			double currentDiscountAmount = order.getDiscounts();
			double discountAmount = getDiscountAmount(order);
			order.setDiscounts(currentDiscountAmount + discountAmount);
			DiscountLineItem lineItem = new DiscountLineItem();
			lineItem.setDescription(description);
			lineItem.setAmount(discountAmount);
			if(order.getDiscountDetails() == null){
				order.setDiscountDetails(new ArrayList<>());
			}
			order.getDiscountDetails().add(lineItem);
			if(nextIfDiscountApplied != null){
				order = nextIfDiscountApplied.applyDiscount(Optional.of(order));
			}
		}
		else if(nextIfDiscountNotApplied != null){
			order = nextIfDiscountNotApplied.applyDiscount(Optional.of(order));
		}
		return order;
	}
	
	protected double formatCurrency(double value){
		 DecimalFormat currencyFormat = new DecimalFormat("#.00");
		 return Double.valueOf(currencyFormat.format(value));
	}
}