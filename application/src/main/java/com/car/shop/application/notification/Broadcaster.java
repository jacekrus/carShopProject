package com.car.shop.application.notification;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broadcaster implements Serializable {
	
	private static final long serialVersionUID = -7299231256272023734L;
	
	static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface BroadcastListener {
        void receiveBroadcast(AbstractNotification notification);
    }

    private static LinkedList<BroadcastListener> listeners =
        new LinkedList<BroadcastListener>();

    public static synchronized void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static synchronized void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

	public static synchronized void broadcast(final AbstractNotification notification) {
		for (final BroadcastListener listener : listeners) {
			executorService.execute(() -> listener.receiveBroadcast(notification));
		}
	}
}
