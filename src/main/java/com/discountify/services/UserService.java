package com.discountify.services;

import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discountify.data.UserRepository;
import com.discountify.pojo.User;

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
	@Autowired
	private UserRepository userDB;

	public UserService() {
	}

	public UserService(UtilService utilService, UserRepository userDB) {
		this.util = utilService;
		this.userDB = userDB;
	}

	public Optional<User> getUserById(int userid) {
		return Optional.ofNullable(userDB.findOne(userid));
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