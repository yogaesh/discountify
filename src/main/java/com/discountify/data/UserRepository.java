package com.discountify.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.discountify.pojo.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{}