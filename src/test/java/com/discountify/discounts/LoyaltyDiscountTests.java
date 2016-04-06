package com.discountify.discounts;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.pojo.Order;
import com.discountify.pojo.User;
import com.discountify.services.UtilService;

public class LoyaltyDiscountTests extends GenericDiscountTests {

	private UtilService utilService;
	
	public LoyaltyDiscountTests() {
		utilService = Mockito.mock(UtilService.class);
		Mockito.when(utilService.isDateOverTwoYearsBack(getPastDate(2, ChronoUnit.YEARS))).thenReturn(false);
		Mockito.when(utilService.isDateOverTwoYearsBack(getPastDate(25, ChronoUnit.MONTHS))).thenReturn(true);
		discount = new LoyaltyDiscount();
		discount.setUtilService(utilService);
		discountValue = new BigDecimal("0.05");
	}
	
	@Test
	public void checkApplicabilityNonQualifyingUser() throws ParseException {
		Order order = new Order();
		order.setUserid(1);
		User user = new User();
		user.setId(1);
		user.setCreatedDate(getPastDate(2,ChronoUnit.YEARS));
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(1)).thenReturn(Optional.of(user));
		assertEquals(false, discount.checkApplicability(Optional.of(order)));
	}

	@Test
	public void checkApplicabilityQualifyingUser() {
		Order order = new Order();
		order.setUserid(2);
		User user = new User();
		user.setId(2);
		user.setCreatedDate(getPastDate(25, ChronoUnit.MONTHS));
		discount.setUserService(userService);
		Mockito.when(discount.userService.getUserById(2)).thenReturn(Optional.of(user));
		assertEquals(true, discount.checkApplicability(Optional.of(order)));	
	}
	
	private Date getPastDate(int displacement, ChronoUnit unit){
		return Date.from(LocalDate.now().minus(displacement, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
