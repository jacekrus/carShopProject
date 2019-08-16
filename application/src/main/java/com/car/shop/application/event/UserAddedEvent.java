package com.car.shop.application.event;

public class UserAddedEvent {
	
	private final long userId;
	
	private final String username;
	
	private final String password;

	public UserAddedEvent(long userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
