package com.car.shop.application.utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class LoginUtils {
	
	private LoginUtils() {}
	
	public static void showNotification(String text, Type type, Position position) {
		Notification notification = new Notification(text, type);
		notification.setDelayMsec(500);
		notification.setPosition(position);
		notification.show(Page.getCurrent());
	}
	
	public static String getLoggedInUsername() {
		return String.valueOf(UI.getCurrent().getSession().getSession().getAttribute("user"));
	}

}
