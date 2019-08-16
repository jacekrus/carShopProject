package com.car.shop.application.event;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

import com.car.shop.application.notification.Broadcaster;
import com.car.shop.application.notification.UserAddedNotification;
import com.car.shop.application.notification.UserDeletedNotification;

public class ServerEventObserver {
	
	public void onUserAdded(@Observes(during=TransactionPhase.AFTER_SUCCESS) UserAddedEvent evt) {
		Broadcaster.broadcast(new UserAddedNotification(evt.getUserId(), evt.getUsername(), evt.getPassword()));
	}
	
	public void onUserDeleted(@Observes(during=TransactionPhase.AFTER_SUCCESS) UserDeletedEvent evt) {
		Broadcaster.broadcast(new UserDeletedNotification(evt.getUserId()));
	}

}
