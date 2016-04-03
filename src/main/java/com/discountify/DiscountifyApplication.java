package com.discountify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.discountify.services.DiscountService;
import com.discountify.services.UserService;
import com.discountify.services.UtilService;

@SpringBootApplication
public class DiscountifyApplication {
	
	@Autowired
	UtilService utilService;
	@Autowired
	DiscountService discountService;
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(DiscountifyApplication.class, args);
	}
}
