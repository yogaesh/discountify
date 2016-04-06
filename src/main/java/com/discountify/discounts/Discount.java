package com.discountify.discounts;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private BigDecimal discountValue;
	private int purchaseUnits;
	
	@Autowired
	protected UserService userService;
	@Autowired
	protected UtilService utilService;
	
	protected abstract boolean checkApplicability(Optional<Order> order);
	protected BigDecimal getDiscountAmount(Order order){
		
		List<Item> items = order.getItems();
		
		BigDecimal total = order.getTotalAmount();
		BigDecimal currentDiscount = order.getDiscounts();
		
		if(discountType.equals("absolute")){
			if(total.equals(BigDecimal.ZERO)){
				total = utilService.getSubtotalExcludingCategories(items, new ArrayList<>());
			}
			
			if(purchaseUnits == 0){
				return discountValue;
			}else{
				return total.subtract(currentDiscount).divide(new BigDecimal(purchaseUnits)).setScale(0, RoundingMode.FLOOR).multiply(discountValue).setScale(2, RoundingMode.HALF_UP);
			}
			
		}
		else{
			return discountValue.multiply(utilService.getSubtotalExcludingCategories(items, Arrays.asList(ItemCategory.GROCERY))).setScale(2, RoundingMode.HALF_UP);
		}
		
	}
	
	public Order applyDiscount(Optional<Order> orderStream){
		Order order = orderStream.get();
		if(checkApplicability(orderStream)){
			BigDecimal currentDiscountAmount = order.getDiscounts();
			BigDecimal discountAmount = getDiscountAmount(order);
			order.setDiscounts(currentDiscountAmount.add(discountAmount).setScale(2, RoundingMode.HALF_UP));
			DiscountLineItem lineItem = new DiscountLineItem();
			lineItem.setDescription(description);
			lineItem.setAmount(discountAmount.setScale(2, RoundingMode.HALF_UP));
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
}