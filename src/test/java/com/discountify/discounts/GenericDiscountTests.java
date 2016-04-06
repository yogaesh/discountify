package com.discountify.discounts;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

public abstract class GenericDiscountTests {

	protected Discount discount;
	protected BigDecimal discountValue;
	protected int purchaseUnits;
	protected UserService userService = Mockito.mock(UserService.class);
	
	@Test
	public void checkApplicabilityEmptyOrder() {
		Order order = new Order();
		assertEquals(false, discount.checkApplicability(Optional.of(order)));
	}
	
	@Test
	public void getDiscountAmountValidItems() {
		
		BigDecimal expectedDiscount = calculateExpectedDiscount();
		
		UtilService utilService = new UtilService();
		discount.setUtilService(utilService);
		
		assertEquals(expectedDiscount, discount.getDiscountAmount(generateSampleOrder()));
	}
	
	protected BigDecimal calculateExpectedDiscount(){
		return discountValue.multiply(new BigDecimal("30.98")).setScale(2, RoundingMode.HALF_UP);
	}
	
	protected Order generateSampleOrder(){
		Order order = new Order();
		List<Item> items = new ArrayList<>();
		Item item1 = new Item();
		item1.setId(1);
		item1.setDescription("Shampoo");
		item1.setCategory(ItemCategory.FMCG);
		item1.setPrice(new BigDecimal("5.99"));
		items.add(item1);
		
		Item item2 = new Item();
		item2.setId(2);
		item2.setDescription("Banana");
		item2.setCategory(ItemCategory.GROCERY);
		item2.setPrice(new BigDecimal("3.99"));
		items.add(item2);

		Item item3 = new Item();
		item3.setId(3);
		item3.setDescription("Milk");
		item3.setCategory(ItemCategory.GROCERY);
		item3.setPrice(new BigDecimal("4.99"));
		items.add(item3);

		Item item4 = new Item();
		item4.setId(4);
		item4.setDescription("Cookware");
		item4.setCategory(ItemCategory.HOME);
		item4.setPrice(new BigDecimal("24.99"));
		items.add(item4);

		order.setItems(items);
		return order;
	}

}
