package com.discountify.services;

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discountify.exception.DiscountifyException;
import com.discountify.pojo.User;
import com.discountify.pojo.UserList;
import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class UserService {
	private Map<Integer, User> map;
	@Autowired
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	private UtilService util;

	public UserService() {
	}

	public UserService(UtilService utilService) {
		this.util = utilService;
	}

	@PostConstruct
	public void init() throws DiscountifyException {
		String usersListJson = util.getResourceFileContents("seed/Users.json");
		Gson gson = new Gson();
		UserList userList = gson.fromJson(usersListJson, UserList.class);
		this.setMap(util.getUserMapFromUserList(userList.getUsers()));
	}

	public Optional<User> getUserById(int userid) {
		return Optional.ofNullable(map.get(userid));
	}

	public boolean isUserEmployee(int userid) {
		return getUserById(userid).filter(user -> user.isEmployee()).isPresent();
	}

	public boolean isUserAffiliate(int userid) {
		return getUserById(userid).filter(user -> user.isAffiliate()).isPresent();
	}

	public boolean isUserLoyal(int userid) {
		return getUserById(userid).filter(user -> util.isDateOverTwoYearsBack(user.getCreatedDate())).isPresent();
	}
}