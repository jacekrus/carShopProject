package com.car.shop.application.view;

import com.car.shop.application.notification.Broadcaster;
import com.car.shop.application.notification.LoginNotification;
import com.car.shop.application.service.AuthenticationService;
import com.car.shop.application.utils.LoginUtils;
import com.vaadin.shared.Position;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class LoginView extends LoginForm {

	private static final long serialVersionUID = -7781949587317897538L;
	
	private AuthenticationService authSvc;
	
	public LoginView(AuthenticationService authSvc) {
		this.authSvc = authSvc;
		this.addLoginListener(this::doLogin);
	}
	
	private void doLogin(LoginEvent event) {
		boolean userAuthenticated = authSvc.login(event.getLoginParameter("username"), event.getLoginParameter("password"));
		if(userAuthenticated) {
			getUI().getSession().getSession().setAttribute("user", event.getLoginParameter("password"));
			Broadcaster.broadcast(new LoginNotification(UI.getCurrent().getSession().getSession().getId()));
		}
		else {
			LoginUtils.showNotification("Wrong username or password", Type.ERROR_MESSAGE, Position.BOTTOM_CENTER);
		}
	}

}
