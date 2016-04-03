package com.discountify.services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.discountify.exception.DiscountifyException;
import com.discountify.pojo.User;

public class UtilServiceTests {
	
	UtilService utilService = new UtilService();

	@Test(expected = DiscountifyException.class)
	public void getResourceFileContentsNullFilename() throws DiscountifyException {
		utilService.getResourceFileContents(null);
	}
	
	@Test(expected = DiscountifyException.class)
	public void getResourceFileContentsInvalidFilename() throws DiscountifyException {
		utilService.getResourceFileContents("thisisnotavalidfilename");
	}
	
	@Test
	public void getResourceFileContentsValidFilename() throws DiscountifyException {
		assertEquals("Test Content", utilService.getResourceFileContents("test/TestContent"));
	}
	
	@Test
	public void getUserMapFromNullUserList() {
		assertEquals(null, utilService.getUserMapFromUserList(null));
	}
	
	@Test
	public void getUserMapFromEmptyUserList() {
		List<User> userList = new ArrayList<>();
		Map<Integer, User> userMap = new HashMap<>();
		assertEquals(userMap, utilService.getUserMapFromUserList(userList));
	}

	@Test
	public void getUserMapFromValidUserList() {
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setAffiliate(false);
		user.setEmployee(true);
		user.setLoyal(false);
		user.setName("Test Yo");
		user.setCreatedDate(new Date());
		userList.add(user);
		Map<Integer, User> userMap = new HashMap<>();
		userMap.put(1, user);
		assertEquals(userMap, utilService.getUserMapFromUserList(userList));
	}
	
	@Test
	public void isDateOverTwoYearsBackInvalid() throws ParseException {
		assertEquals(false, utilService.isDateOverTwoYearsBack(getDateFromString("2016-04-01")));
	}
	
	@Test
	public void isDateOverTwoYearsBackValid() throws ParseException {
		assertEquals(true, utilService.isDateOverTwoYearsBack(getDateFromString("2014-04-01")));
	}
		
	private Date getDateFromString(String dateString) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(dateString);
	}


}
