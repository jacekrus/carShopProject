package com.car.shop.application.notification;

public abstract class UserChangedNotification extends AbstractNotification {
	
	private final long userId;
	
	public interface UserChangedNotificationVisitor<V> {
		
		V visit(UserAddedNotification notification);
		
		V visit(UserDeletedNotification notification);
		
	}
	
	public UserChangedNotification(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	@Override
	public <V> V accept(NotificationVisitor<V> visitor) {
		return visitor.visit(this);
	}
	
	public abstract <V> V accept(UserChangedNotificationVisitor<V> visitor);
	
}
