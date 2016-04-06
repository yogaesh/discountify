package com.discountify.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.discountify.data.UserRepository;
import com.discountify.pojo.User;
import com.discountify.services.UtilService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@Component
public class DBSeeder implements CommandLineRunner {
	 
    @Autowired
	UserRepository userDB;
    @Autowired
    UtilService util;
    @Autowired
    Gson gson;
	@Override
    public void run(String... strings) throws Exception {
		String usersListJson = util.getResourceFileContents("seed/Users.json");
		
		@SuppressWarnings("serial")
		List<User> userList = gson.fromJson(usersListJson, new TypeToken<ArrayList<User>>(){}.getType());
		userDB.save(userList);
    }
}