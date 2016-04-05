package com.discountify.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
	private int id;
	private String name;
	private boolean isEmployee, isAffiliate;
	private Date createdDate;
	
	@Override
	public String toString(){
		return "Id: " + this.getId() 
		+ ", Name: " + this.getName() 
		+ ", isEmployee? " + this.isEmployee() 
		+ ", isAffiliate: " + this.isAffiliate() 
		+ ", createdDate: " + this.getCreatedDate();
	}
}
