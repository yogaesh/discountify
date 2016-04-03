package com.discountify.discounts;


import com.discountify.pojo.Order;
import com.discountify.pojo.User;

public class AffiliateDiscount extends Discount {
	
	public AffiliateDiscount(){
		this.setId(2);
		this.setDescription("10% discount for employees");
		this.setName("AffiliateDiscount");
		this.setDiscountValue(0.1);
		this.setDiscountType("percentage");
	}
	
	@Override
	protected boolean checkApplicability(Order order) {
		if(order == null || order.getUserid() == 0){
			return false;
		}
		User user = userService.getUserById(order.getUserid());
		return (user == null ? false : user.isAffiliate()); 
	}

}
