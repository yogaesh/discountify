package com.discountify.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;

@Service
public class DiscountService {
	public double getSubtotalExcludingCategories(List<Item> items, List<ItemCategory> categoriesToExclude){
		
		if (items == null){
			return 0;
		}
		
		double total = 0;
		boolean includeAll = false;
		
		if(categoriesToExclude == null || categoriesToExclude.isEmpty()){
			includeAll = true;
		}
		
		for(Item item : items){
			if(!includeAll && categoriesToExclude.contains(item.getCategory())){
				continue;
			}
			total += item.getPrice();
		}
		
		return total;
	}
	
}
