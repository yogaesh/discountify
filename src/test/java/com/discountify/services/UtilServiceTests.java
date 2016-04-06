package com.discountify.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.discountify.exception.DiscountifyException;
import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
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
	
	@Test
	public void getSubtotalExcludingCategoriesEmptyCategoriesList() {
		ArrayList<Item> items = new ArrayList<>();
		
		Item item1 = new Item();
		item1.setCategory(ItemCategory.GROCERY);
		item1.setDescription("Banana");
		item1.setId(1);
		item1.setPrice(new BigDecimal("5.99"));
		items.add(item1);
		
		Item item2 = new Item();
		item2.setCategory(ItemCategory.FMCG);
		item2.setDescription("Shampoo");
		item2.setId(2);
		item2.setPrice(new BigDecimal("8.99"));
		items.add(item2);

		assertEquals(new BigDecimal("14.98"), utilService.getSubtotalExcludingCategories(items, new ArrayList<ItemCategory>()));
	}
	
	@Test
	public void getSubtotalExcludingCategories() {
		ArrayList<ItemCategory> categories = new ArrayList<>();
		categories.add(ItemCategory.GROCERY);
		
		ArrayList<Item> items = new ArrayList<>();
		
		Item item1 = new Item();
		item1.setCategory(ItemCategory.GROCERY);
		item1.setDescription("Banana");
		item1.setId(1);
		item1.setPrice(new BigDecimal("5.99"));
		items.add(item1);
		
		Item item2 = new Item();
		item2.setCategory(ItemCategory.FMCG);
		item2.setDescription("Shampoo");
		item2.setId(2);
		item2.setPrice(new BigDecimal("8.99"));
		items.add(item2);
		
		Item item3 = new Item();
		item3.setCategory(ItemCategory.FMCG);
		item3.setDescription("Soap");
		item3.setId(3);
		item3.setPrice(new BigDecimal("8.99"));
		items.add(item3);

		assertEquals(new BigDecimal("17.98"), utilService.getSubtotalExcludingCategories(items, categories));
	}

		
	private Date getDateFromString(String dateString) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(dateString);
	}


}
