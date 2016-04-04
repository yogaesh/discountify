package com.discountify.discounts;


import org.springframework.stereotype.Component;

import com.discountify.pojo.Order;
import com.discountify.pojo.User;

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
	protected boolean checkApplicability(Order order) {
		if(order == null || order.getUserid() == 0){
			return false;
		}
		if(discountService == null){
			System.out.println("Discount service is also null");
		}
		User user = userService.getUserById(order.getUserid());
		return (user == null ? false : user.isEmployee()); 
	}

}
