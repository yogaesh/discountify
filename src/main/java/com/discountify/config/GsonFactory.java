package com.discountify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class GsonFactory {
	
	@Bean
	public Gson getGson(){
		return new Gson();
	}

}
