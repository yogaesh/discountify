package com.discountify.discounts;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.discountify.pojo.Order;

@Component
public class LoyaltyDiscount extends Discount {

	public LoyaltyDiscount(){
		this.setId(1);
		this.setDescription("5% discount for loyal customers");
		this.setName("LoyaltyDiscount");
		this.setDiscountValue(0.05);
		this.setDiscountType("percentage");
	}
	@Override
	protected boolean checkApplicability(Optional<Order> orderStream) {
		return orderStream.filter(order -> order.getUserid() > 0)
				.flatMap(order -> userService.getUserById(order.getUserid()))
				.filter(user -> utilService.isDateOverTwoYearsBack(user.getCreatedDate()))
				.isPresent();

		//		if(order == null || order.getUserid() == 0){
//			return false;
//		}
//		User user = userService.getUserById(order.getUserid());
//		return utilService.isDateOverTwoYearsBack(user.getCreatedDate());
	}

}
