package com.discountify.discounts;


import java.util.Optional;

import org.springframework.stereotype.Component;

import com.discountify.pojo.Order;

@Component
public class EmployeeDiscount extends Discount{
	
	public EmployeeDiscount(){
		this.setId(1);
		this.setDescription("30% discount for employees");
		this.setName("EmployeeDiscount");
		this.setDiscountValue(0.3);
		this.setDiscountType("percentage");
	}
	
	@Override
	protected boolean checkApplicability(Optional<Order> order) {
		return order.filter(orderEntry -> orderEntry.getUserid() > 0)
				.flatMap(validOrder -> userService.getUserById(validOrder.getUserid()))
				.filter(user -> user.isEmployee()).isPresent();
	}

}
