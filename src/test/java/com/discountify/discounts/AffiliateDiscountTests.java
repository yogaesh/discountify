package com.discountify.discounts;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.pojo.Order;
import com.discountify.pojo.User;

public class AffiliateDiscountTests extends GenericDiscountTests {

	public AffiliateDiscountTests() {
		discount = new AffiliateDiscount();
		discountValue = 0.1;
	}
	
	@Test
	public void checkApplicabilityNonAffiliateUser() {
		Order order = new Order();
		order.setUserid(1);
		User user = new User();
		user.setId(1);
		user.setAffiliate(false);
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(1)).thenReturn(user);
		assertEquals(false, discount.checkApplicability(order));
	}

	@Test
	public void checkApplicabilityAffiliateUser() {
		Order order = new Order();
		order.setUserid(2);
		User user = new User();
		user.setId(2);
		user.setAffiliate(true);
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(2)).thenReturn(user);
		assertEquals(true, discount.checkApplicability(order));	
	}
}
