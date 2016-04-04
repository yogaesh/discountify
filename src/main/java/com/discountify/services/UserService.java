package com.discountify.services;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discountify.exception.DiscountifyException;
import com.discountify.pojo.User;
import com.discountify.pojo.UserList;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class UserService {
	private Map<Integer, User> map;
	@Autowired
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

	public User getUserById(int userid) {
		return map.get(userid);
	}

	public boolean isUserEmployee(int userid) {
		return checkUserProperty(userid, "employee");
	}

	public boolean isUserAffiliate(int userid) {
		return checkUserProperty(userid, "affiliate");
	}

	public boolean isUserLoyal(int userid) {
		return checkUserProperty(userid, "loyal");
	}

	private boolean checkUserProperty(int userid, String property) {
		User user = map.get(userid);
		if (user == null) {
			return false;
		}

		switch (property) {
		case "employee":
			return user.isEmployee();
		case "affiliate":
			return user.isAffiliate();
		case "loyal":
			return util.isDateOverTwoYearsBack(user.getCreatedDate());
		default:
			return false;
		}
	}
}