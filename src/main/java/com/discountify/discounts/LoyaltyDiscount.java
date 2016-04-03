package com.discountify.discounts;

import com.discountify.pojo.Order;
import com.discountify.pojo.User;

public class LoyaltyDiscount extends Discount {

	public LoyaltyDiscount(){
		this.setId(1);
		this.setDescription("5% discount for employees");
		this.setName("LoyaltyDiscount");
		this.setDiscountValue(0.05);
		this.setDiscountType("percentage");
	}
	@Override
	protected boolean checkApplicability(Order order) {
		if(order == null || order.getUserid() == 0){
			return false;
		}
		User user = userService.getUserById(order.getUserid());
		return utilService.isDateOverTwoYearsBack(user.getCreatedDate());
	}

}
