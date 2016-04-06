package com.discountify.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
	@GeneratedValue
	@Id
	private int id;
	
	private String name;
	private boolean isEmployee, isAffiliate;
	private Date createdDate;
	
	public User(){}
	public User(int id, String name, boolean isEmployee, boolean isAffiliate, Date createdDate){
		this.id = id;
		this.name = name;
		this.isAffiliate = isAffiliate;
		this.isEmployee = isEmployee;
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString(){
		return "Id: " + this.getId() 
		+ ", Name: " + this.getName() 
		+ ", isEmployee? " + this.isEmployee() 
		+ ", isAffiliate: " + this.isAffiliate() 
		+ ", createdDate: " + this.getCreatedDate();
	}
}
