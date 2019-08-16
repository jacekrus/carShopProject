package com.car.shop.application.service;

public interface AuthenticationService {
	
	boolean login(String userName, String password);
	
	String encryptPassword(String password);

}
