package com.discountify.discounts;



import java.util.Optional;

import org.springframework.stereotype.Component;

import com.discountify.pojo.Order;

@Component
public class AffiliateDiscount extends Discount {
	
	public AffiliateDiscount(){
		this.setId(2);
		this.setDescription("10% discount for affiliates");
		this.setName("AffiliateDiscount");
		this.setDiscountValue(0.1);
		this.setDiscountType("percentage");
	}
	
	@Override
	protected boolean checkApplicability(Optional<Order> order) {
		
		return order.filter(orderEntry -> orderEntry.getUserid() > 0)
				.flatMap(validOrder -> userService.getUserById(validOrder.getUserid()))
				.filter(user -> user.isAffiliate()).isPresent();
	}

}
