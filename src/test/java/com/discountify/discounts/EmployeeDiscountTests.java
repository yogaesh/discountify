package com.discountify.discounts;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.pojo.Order;
import com.discountify.pojo.User;

public class EmployeeDiscountTests extends GenericDiscountTests {
	
	public EmployeeDiscountTests() {
		discount = new EmployeeDiscount();
		discountValue = 0.3;
	}
	
	@Test
	public void checkApplicabilityNonEmployeeUser() {
		Order order = new Order();
		order.setUserid(1);
		User user = new User();
		user.setId(1);
		user.setEmployee(false);
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(1)).thenReturn(Optional.of(user));
		assertEquals(false, discount.checkApplicability(Optional.of(order)));
	}

	@Test
	public void checkApplicabilityEmployeeUser() {
		Order order = new Order();
		order.setUserid(2);
		User user = new User();
		user.setId(2);
		user.setEmployee(true);
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(2)).thenReturn(Optional.of(user));
		assertEquals(true, discount.checkApplicability(Optional.of(order)));	
	}

}
