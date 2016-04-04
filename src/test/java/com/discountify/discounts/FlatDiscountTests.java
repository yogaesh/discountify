package com.discountify.discounts;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.Order;
import com.discountify.services.UtilService;

public class FlatDiscountTests extends GenericDiscountTests {
	
	UtilService utilService = Mockito.mock(UtilService.class);
	
	public FlatDiscountTests() {
		discount = new FlatDiscount();
	}
	
	@Test
	public void checkApplicabilityNonQualifyingOrder() throws ParseException {
		Order order = super.generateSampleOrder();
		discount.setUtilService(utilService);
		Mockito.when(discount.utilService.getSubtotalExcludingCategories(order.getItems(), null)).thenReturn(39.96);
		assertEquals(false, discount.checkApplicability(order));
	}

	@Test
	public void checkApplicabilityQualifyingOrder() {
		Order order = generateSampleOrder();
		discount.setUtilService(utilService);
		Mockito.when(discount.utilService.getSubtotalExcludingCategories(order.getItems(), null)).thenReturn(882.96);
		assertEquals(true, discount.checkApplicability(order));	
	}
	
	@Override
	protected double calculateExpectedDiscount(){
		return 40;
	}
	
	@Override
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
		
		Item item5 = new Item();
		item5.setId(5);
		item5.setDescription("Pillow");
		item5.setCategory(ItemCategory.HOME);
		item5.setPrice(70);
		items.add(item5);
		
		Item item6 = new Item();
		item6.setId(6);
		item6.setDescription("Mattress");
		item6.setCategory(ItemCategory.HOME);
		item6.setPrice(773);
		items.add(item6);

		order.setItems(items);
		return order;

	}

}
