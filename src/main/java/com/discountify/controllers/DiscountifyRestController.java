package com.discountify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discountify.services.UserService;

@RestController
@RequestMapping("/orders/{orderId}")
public class DiscountifyRestController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("discount")
	public String getDiscountForOrder(@RequestBody String orderJson) {
		
		//TODO: Expand implementation
		return null;
	}
}