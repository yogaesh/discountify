package com.discountify.services;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.discountify.pojo.User;
import com.discountify.exception.DiscountifyException;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

public class UserServiceTests {
	
	private UtilService utilService;
	private UserService userService;
	private Map<Integer, User> userMap;
	private List<User> userList;
	
	public UserServiceTests() throws DiscountifyException, ParseException{
		String userListJson = "{\"userList\":[{\"id\":1,\"name\":\"Cool Yo\",\"isEmployee\":true,\"isAffiliate\":true,\"createdDate\":\"2010-01-01\"},{\"id\":2,\"name\":\"Dull Yo\",\"isEmployee\":false,\"isAffiliate\":false,\"createdDate\":\"2016-04-01\"}]}";
		initUserList();
		initUserMap();
		utilService = Mockito.mock(UtilService.class);
		Mockito.when(utilService.getResourceFileContents(Matchers.anyString())).thenReturn(userListJson);
		Mockito.when(utilService.getUserMapFromUserList(Matchers.anyListOf(User.class))).thenReturn(userMap);
		Mockito.when(utilService.isDateOverTwoYearsBack(new SimpleDateFormat("yyyy-MM-dd").parse("2010-01-01"))).thenReturn(true);
		Mockito.when(utilService.isDateOverTwoYearsBack(new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-01"))).thenReturn(false);
		userService = new UserService(utilService);
		userService.init();
	}
	
	private void initUserMap() throws ParseException{
		if(userMap == null){
			userMap = new HashMap<>();
			userMap.put(1, userList.get(0));
			userMap.put(2, userList.get(1));
		}
	}
	
	private void initUserList() throws ParseException{
		
		if(userList == null){
			userList = new ArrayList<>(); 
			
			User user1 = new User();
			user1.setAffiliate(true);
			user1.setEmployee(true);
			user1.setId(1);
			
			User user2 = new User();
			user2.setAffiliate(false);
			user2.setEmployee(false);
			user2.setId(2);
			
			DateFormat format = new SimpleDateFormat("yyyy-dd-MM");
			user1.setCreatedDate(format.parse("2010-01-01"));
			user2.setCreatedDate(format.parse("2016-04-01"));
			
			userList.add(user1);
			userList.add(user2);

		}
		
	}

	@Test
	public void init() throws DiscountifyException, ParseException {
		//userService.init();
		assertEquals(userMap,userService.getMap());
	}
	
	@Test
	public void getUserByIdZero() throws DiscountifyException, ParseException {
		assertEquals(null,userService.getUserById(0));
	}
	
	@Test
	public void getUserByValidId() throws DiscountifyException, ParseException {
		assertEquals(userMap.get(1),userService.getUserById(1));
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


}
