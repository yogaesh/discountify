package com.discountify.discounts;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.discountify.pojo.Order;

@Component
public class LoyaltyDiscount extends Discount {

	public LoyaltyDiscount(){
		this.setId(1);
		this.setDescription("5% discount for loyal customers");
		this.setName("LoyaltyDiscount");
		this.setDiscountValue(new BigDecimal("0.05"));
		this.setDiscountType("percentage");
	}
	@Override
	protected boolean checkApplicability(Optional<Order> orderStream) {
		return orderStream.filter(order -> order.getUserid() > 0)
				.flatMap(order -> userService.getUserById(order.getUserid()))
				.filter(user -> utilService.isDateOverTwoYearsBack(user.getCreatedDate()))
				.isPresent();
	}

}
