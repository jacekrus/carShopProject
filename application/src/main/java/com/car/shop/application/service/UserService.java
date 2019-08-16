package com.car.shop.application.service;

import java.util.List;

import com.car.shop.application.model.UserEntity;

public interface UserService {
	
	void addUser(UserEntity userEntity);
	
	void removeUser(UserEntity userEntity);
	
	List<UserEntity> findAllUsers(); 
	
	UserEntity findUserByName(String username);
	
}
