package com.car.shop.application.notification;

public abstract class AbstractNotification {
	
	public interface NotificationVisitor<V> {
		
		V visit(UserChangedNotification notification);
		
		V visit(LoginNotification notification);
		
	}
	
	public abstract <V> V accept(NotificationVisitor<V> visitor);

}
