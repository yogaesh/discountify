package com.discountify.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscountifyException extends Exception {
	private static final long serialVersionUID = 1L;
	
	ErrorDefinitions errorDefintion;
	Throwable originalError;
	
	public DiscountifyException(Throwable error, ErrorDefinitions errorDef){
		this.originalError = error;
		this.errorDefintion = errorDef;
	}
	
	@Override
	public String getMessage(){
		return this.errorDefintion.toString();
	}
	
}
