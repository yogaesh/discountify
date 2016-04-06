package com.discountify.services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.pojo.User;
import com.discountify.data.UserRepository;
import com.discountify.exception.DiscountifyException;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

public class UserServiceTests {
	
	private UtilService utilService;
	private UserService userService;
	private UserRepository userDB;
	private User testUser1, testUser2;
	
	public UserServiceTests() throws DiscountifyException, ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		testUser1 = new User(1, "Cool Yo", true, true, format.parse("2010-01-01"));
		testUser2 = new User(2, "Dull Yo", false, false, format.parse("2016-04-01"));

		utilService = Mockito.mock(UtilService.class);
		Mockito.when(utilService.isDateOverTwoYearsBack(format.parse("2010-01-01"))).thenReturn(true);
		Mockito.when(utilService.isDateOverTwoYearsBack(format.parse("2016-04-01"))).thenReturn(false);

		userDB = Mockito.mock(UserRepository.class);
		Mockito.when(userDB.findOne(1)).thenReturn(testUser1);
		Mockito.when(userDB.findOne(2)).thenReturn(testUser2);
		
		userService = new UserService(utilService, userDB);
	}
	
	@Test
	public void getUserByIdZero() throws DiscountifyException, ParseException {
		assertEquals(Optional.empty(), userService.getUserById(0));
	}
	
	@Test
	public void getUserByValidId() throws DiscountifyException, ParseException {
		assertEquals(testUser1,userService.getUserById(1).get());
	}

	@Test
	public void isUserEmployeeValid() throws DiscountifyException, ParseException {
		assertEquals(true,userService.isUserEmployee(1));
	}
	
	@Test
	public void isUserEmployeeInvalid() throws DiscountifyException, ParseException {
		assertEquals(false,userService.isUserEmployee(2));
	}
	
	@Test
	public void isUserAffiliateValid() throws DiscountifyException, ParseException {
		assertEquals(true,userService.isUserAffiliate(1));
	}
	
	@Test
	public void isUserAffiliateInvalid() throws DiscountifyException, ParseException {
		assertEquals(false,userService.isUserAffiliate(2));
	}
	
	@Test
	public void isUserLoyalValid() throws DiscountifyException, ParseException {
		assertEquals(true,userService.isUserLoyal(1));
	}
	
	@Test
	public void isUserLoyalInvalid() throws DiscountifyException, ParseException {
		assertEquals(false,userService.isUserLoyal(2));
	}
	
	@Test
	public void isUserLoyalNonExistentUser() throws DiscountifyException, ParseException {
		assertEquals(false,userService.isUserLoyal(99));
	}

}
