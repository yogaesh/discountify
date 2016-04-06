package com.discountify.services;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.discountify.discounts.Discount;
import com.discountify.pojo.Order;

@Service
public class DiscountService {
	
	@Resource(name="firstDiscountInChain")
	private Discount firstDiscount;
	public Order getDiscountAmount(Order originalOrder){
		return firstDiscount.applyDiscount(Optional.of(originalOrder));
	}
	
}
