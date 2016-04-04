package com.discountify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.discountify.discounts.AffiliateDiscount;
import com.discountify.discounts.EmployeeDiscount;
import com.discountify.discounts.FlatDiscount;
import com.discountify.discounts.LoyaltyDiscount;
import com.discountify.pojo.Order;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DiscountService {
	@Autowired
	private EmployeeDiscount employeeDiscount;
	@Autowired
	private AffiliateDiscount affiliateDiscount;
	@Autowired
	private LoyaltyDiscount loyaltyDiscount;
	@Autowired
	private FlatDiscount flatDiscount;
	
	private void chainDiscounts(){
		employeeDiscount.setNextIfDiscountApplied(flatDiscount);
		employeeDiscount.setNextIfDiscountNotApplied(affiliateDiscount);
		affiliateDiscount.setNextIfDiscountApplied(flatDiscount);
		affiliateDiscount.setNextIfDiscountNotApplied(loyaltyDiscount);
		loyaltyDiscount.setNextIfDiscountApplied(flatDiscount);
		loyaltyDiscount.setNextIfDiscountNotApplied(flatDiscount);
	}
	
	public Order getDiscountAmount(Order originalOrder){
		chainDiscounts();
		return employeeDiscount.applyDiscount(originalOrder);
	}
	
}
