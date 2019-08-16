package com.car.shop.application.notification;

public class UserAddedNotification extends UserChangedNotification {
	
	private final String name;
	
	private final String password;

	public UserAddedNotification(long userId, String name, String password) {
		super(userId);
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}

	@Override
	public <V> V accept(UserChangedNotificationVisitor<V> visitor) {
		return visitor.visit(this);
	}

}
