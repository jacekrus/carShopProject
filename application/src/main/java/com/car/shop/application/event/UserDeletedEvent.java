package com.car.shop.application.event;

public class UserDeletedEvent {
	
	private final long userId;

	public UserDeletedEvent(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

}
