package com.discountify.discounts;

import com.discountify.pojo.Order;

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
	protected boolean checkApplicability(Order order) {
		if(order == null || order.getItems() == null || order.getItems().isEmpty()){
			return false;
		}
		
		double totalAmount = order.getTotalAmount();
		if(totalAmount == 0.0){
			totalAmount = discountService.getSubtotalExcludingCategories(order.getItems(), null);
		}
		
		return totalAmount-order.getDiscounts() > 100 ? true : false;
	}
	
}
