package com.discountify.discounts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.DiscountService;
import com.discountify.services.UserService;

public abstract class GenericDiscountTests {

	protected Discount discount;
	protected double discountValue;
	protected int purchaseUnits;
	protected UserService userService = Mockito.mock(UserService.class);
	
	@Test
	public void checkApplicabilityNullOrder() {
		assertEquals(false, discount.checkApplicability(null));
	}
	
	@Test
	public void checkApplicabilityEmptyOrder() {
		Order order = new Order();
		assertEquals(false, discount.checkApplicability(order));
	}
	
	@Test
	public void getDiscountAmountValidItems() {
		
		double expectedDiscount = calculateExpectedDiscount();
		
		DiscountService discountService = new DiscountService();
		discount.setDiscountService(discountService);
		
		assertEquals(new Double(expectedDiscount), new Double(discount.getDiscountAmount(generateSampleOrder())));
	}
	
	@Test
	public void getDiscountAmountNullOrder() {
		assertEquals(new Double(0), new Double(discount.getDiscountAmount(null)));
	}
	
	@Test
	public void getDiscountAmountEmptyOrder() {
		Order order = new Order();
		assertEquals(new Double(0), new Double(discount.getDiscountAmount(order)));
	}
	
	protected double calculateExpectedDiscount(){
		return discountValue * (5.99 + 24.99);
	}
	
	protected Order generateSampleOrder(){
		Order order = new Order();
		List<Item> items = new ArrayList<>();
		Item item1 = new Item();
		item1.setId(1);
		item1.setDescription("Shampoo");
		item1.setCategory(ItemCategory.FMCG);
		item1.setPrice(5.99);
		items.add(item1);
		
		Item item2 = new Item();
		item2.setId(2);
		item2.setDescription("Banana");
		item2.setCategory(ItemCategory.GROCERY);
		item2.setPrice(3.99);
		items.add(item2);

		Item item3 = new Item();
		item3.setId(3);
		item3.setDescription("Milk");
		item3.setCategory(ItemCategory.GROCERY);
		item3.setPrice(4.99);
		items.add(item3);

		Item item4 = new Item();
		item4.setId(4);
		item4.setDescription("Cookware");
		item4.setCategory(ItemCategory.HOME);
		item4.setPrice(24.99);
		items.add(item4);

		order.setItems(items);
		return order;
	}

}
