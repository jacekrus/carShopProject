package com.car.shop.application.notification;

public class UserDeletedNotification extends UserChangedNotification {

	public UserDeletedNotification(long userId) {
		super(userId);
	}

	@Override
	public <V> V accept(UserChangedNotificationVisitor<V> visitor) {
		return visitor.visit(this);
	}

}
