package com.discountify.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
	private int id;
	private String name;
	private boolean isEmployee, isAffiliate, isLoyal;
	private Date createdDate;
}
