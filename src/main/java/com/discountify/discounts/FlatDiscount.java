package com.discountify.discounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Order;

@Component
public class FlatDiscount extends Discount{

	public FlatDiscount() {
		this.setId(4);
		this.setDescription("$5 discount on every $100 spent");
		this.setName("ValuedCustomerDiscount");
		this.setDiscountValue(5);
		this.setDiscountType("absolute");
		this.setPurchaseUnits(100);
	}
	
	@Override
	protected boolean checkApplicability(Optional<Order> orderStream) {
		List<ItemCategory> list = new ArrayList<>();
		return orderStream.filter(order -> order.getItems() != null)
		.map(order -> utilService.getSubtotalExcludingCategories(order.getItems(), list) - order.getDiscounts())
		.filter(amount -> amount > 100).isPresent();		
	}
	
}
