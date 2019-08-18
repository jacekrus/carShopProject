package com.car.shop.application;

import javax.inject.Inject;

import com.car.shop.application.notification.AbstractNotification;
import com.car.shop.application.notification.Broadcaster;
import com.car.shop.application.notification.Broadcaster.BroadcastListener;
import com.car.shop.application.notification.LoginNotification;
import com.car.shop.application.utils.AuthenticationServiceProvider;
import com.car.shop.application.utils.LoginUtils;
import com.car.shop.application.view.LoginView;
import com.car.shop.application.view.MainView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@Theme("mytheme")
@CDIUI("")
@Push(transport = Transport.WEBSOCKET_XHR)
@PreserveOnRefresh
@PushStateNavigation
public class ApplicationUI extends UI implements BroadcastListener {
	
	private static final long serialVersionUID = 4625071647733527513L;

	@Inject
	private AuthenticationServiceProvider authSvcProvider;
	
	@Inject
	private CDIViewProvider viewProvider;
	
	private HorizontalLayout mainLayout;
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		mainLayout = new HorizontalLayout();
		setUpApplicationLayout(vaadinRequest);
    	mainLayout.setSizeFull();
    	setSizeFull();
    	setContent(mainLayout);
    	Broadcaster.register(this);
    }
	
	@Override
	public void receiveBroadcast(AbstractNotification notification) {
		if(notification instanceof LoginNotification) {
			onLogin((LoginNotification) notification);
		}
	}
	
	@Override
	public void detach() {
		Broadcaster.unregister(this);
		super.detach();
	}
	
	private void setUpApplicationLayout(VaadinRequest vaadinRequest) {
		boolean isSessionActive = vaadinRequest.getWrappedSession().getAttribute("user") != null;
		if (isSessionActive) {
			setUpMainView(false);
		} else {
			setUpLoginView();
		}
	}
	
	private void setUpLoginView() {
		access(() -> {
			mainLayout.removeAllComponents();
			LoginView loginView = new LoginView(authSvcProvider.getAuthSvc());
			mainLayout.addComponent(loginView);
			mainLayout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);
		});
	}
	
	private void setUpMainView(boolean showLoginNotification) {
		access(() -> {
			mainLayout.removeAllComponents();
			MainView mainView = new MainView(viewProvider, mainLayout);
			mainLayout.addComponent(mainView);
			if(showLoginNotification) {
				LoginUtils.showNotification("Login successful!", Type.HUMANIZED_MESSAGE, Position.MIDDLE_CENTER);
			}
		});
	}

	private void onLogin(final LoginNotification notification) {
		String sessionId = getSession().getSession().getId();
		if (sessionId.equals(notification.getHttpSessionId())) {
			setUpMainView(true);
		}
	}
	
}
