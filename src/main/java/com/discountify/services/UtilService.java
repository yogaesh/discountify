package com.discountify.services;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.discountify.exception.DiscountifyException;
import com.discountify.exception.ErrorDefinitions;
import com.discountify.item.categories.ItemCategory;
import com.discountify.pojo.Item;
import com.discountify.pojo.User;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;


@Service
public class UtilService {
	
	public String getResourceFileContents(String filename) throws DiscountifyException{
		if(filename == null){
			throw new DiscountifyException(new NullPointerException("Filename is null"), ErrorDefinitions.FILE);
		}
		try {
			URL url = Resources.getResource(filename);
			return Resources.toString(url, Charsets.UTF_8);
		} catch (IOException | IllegalArgumentException e) {
			throw new DiscountifyException(e, ErrorDefinitions.FILE);
		}
	}
	
	public Map<Integer, User> getUserMapFromUserList(List<User> list){
		if(list == null){
			return null;
		}
		int size = list.size();
		Map<Integer, User> userMap = new HashMap<>();
		for(int index = 0; index < size; index++){
			userMap.put(index + 1, list.get(index));
		}
		return userMap;
	}
	
	public boolean isDateOverTwoYearsBack(Date date){
		LocalDate twoYearsAgo = LocalDate.now().minus(2, ChronoUnit.YEARS);
		Date thresholdDate = Date.from(twoYearsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
 
		if(date.before(thresholdDate)){
			return true;
		}
		return false;
	}
	
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

