package com.discountify.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;

public class DiscountServiceTests {
	
	private DiscountService discountService = new DiscountService();

	@Test
	public void getSubtotalExcludingCategoriesNullCheck() {
		assertEquals(new Double(0), new Double(discountService.getSubtotalExcludingCategories(null, null)));
	}
	
	@Test
	public void getSubtotalExcludingCategoriesEmptyLists() {
		assertEquals(new Double(0), new Double(discountService.getSubtotalExcludingCategories(new ArrayList<Item>(), new ArrayList<ItemCategory>())));
	}

	@Test
	public void getSubtotalExcludingCategoriesEmptyItemsList() {
		ArrayList<ItemCategory> categories = new ArrayList<>();
		categories.add(ItemCategory.GROCERY);
		assertEquals(new Double(0), new Double(discountService.getSubtotalExcludingCategories(new ArrayList<Item>(), categories)));
	}
	
	@Test
	public void getSubtotalExcludingCategoriesEmptyCategoriesList() {
		ArrayList<Item> items = new ArrayList<>();
		
		Item item1 = new Item();
		item1.setCategory(ItemCategory.GROCERY);
		item1.setDescription("Banana");
		item1.setId(1);
		item1.setPrice(5.99);
		items.add(item1);
		
		Item item2 = new Item();
		item2.setCategory(ItemCategory.FMCG);
		item2.setDescription("Shampoo");
		item2.setId(2);
		item2.setPrice(8.99);
		items.add(item2);

		assertEquals(new Double(5.99 + 8.99), new Double(discountService.getSubtotalExcludingCategories(items, null)));
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
		item1.setPrice(5.99);
		items.add(item1);
		
		Item item2 = new Item();
		item2.setCategory(ItemCategory.FMCG);
		item2.setDescription("Shampoo");
		item2.setId(2);
		item2.setPrice(8.99);
		items.add(item2);

		assertEquals(new Double(8.99), new Double(discountService.getSubtotalExcludingCategories(items, categories)));
	}

}
