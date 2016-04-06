package com.discountify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.discountify.discounts.AffiliateDiscount;
import com.discountify.discounts.Discount;
import com.discountify.discounts.EmployeeDiscount;
import com.discountify.discounts.FlatDiscount;
import com.discountify.discounts.LoyaltyDiscount;

@Configuration
public class DiscountInjector {
		
	@Bean(name="firstDiscountInChain")
	public Discount getFirstDiscountInChain(EmployeeDiscount employeeDiscount, 
			AffiliateDiscount affiliateDiscount, 
			LoyaltyDiscount loyaltyDiscount, 
			FlatDiscount flatDiscount){
		
		employeeDiscount.setNextIfDiscountApplied(flatDiscount);
		employeeDiscount.setNextIfDiscountNotApplied(affiliateDiscount);
		affiliateDiscount.setNextIfDiscountApplied(flatDiscount);
		affiliateDiscount.setNextIfDiscountNotApplied(loyaltyDiscount);
		loyaltyDiscount.setNextIfDiscountApplied(flatDiscount);
		loyaltyDiscount.setNextIfDiscountNotApplied(flatDiscount);
		return employeeDiscount;
	}
}
