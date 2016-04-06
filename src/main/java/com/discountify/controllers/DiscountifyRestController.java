package com.discountify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.discountify.pojo.Order;
import com.discountify.services.DiscountService;

@RestController
@RequestMapping("/orders/{orderId}")
public class DiscountifyRestController {
	
	@Autowired
	DiscountService discountService;
	
	@ResponseBody @RequestMapping(value="discount", method = {RequestMethod.POST}, consumes = {"application/json"})
	public Order getDiscountForOrder(@RequestBody Order order) {
		if(order.getItems() == null || order.getItems().isEmpty()){
			return order;
		}
		return discountService.getDiscountAmount(order);
	}
}