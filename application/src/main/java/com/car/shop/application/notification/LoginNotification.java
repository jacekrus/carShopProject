package com.car.shop.application.notification;

public class LoginNotification extends AbstractNotification {
	
	private final String httpSessionId;
	
	public LoginNotification(String httpSessionId) {
		super();
		this.httpSessionId = httpSessionId;
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	@Override
	public <V> V accept(NotificationVisitor<V> visitor) {
		return visitor.visit(this);
	}

}
