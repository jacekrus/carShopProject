package com.car.shop.application.widget;

import java.util.Collection;

import com.car.shop.application.model.UserEntity;
import com.car.shop.application.service.UserService;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AddUserDialog extends Window {
	
	private static final long serialVersionUID = -9081002434624502645L;
	
	private VerticalLayout dialogContent;
	
	private UserService userSvc;
	
	private Collection<UserEntity> users;
	
	public AddUserDialog(UserService userSvc, Collection<UserEntity> users) {
		this.userSvc = userSvc;
		this.users = users;
		dialogContent = initDialogContent();
		this.setContent(dialogContent);
		this.center();
		this.setResizable(false);
		this.setModal(true);
	}
	
	private VerticalLayout initDialogContent() {
		VerticalLayout dialogContent = new VerticalLayout();
		dialogContent.setMargin(true);
		dialogContent.setSpacing(true);
		
		TextField usernameField = new TextField("Username: ");
		
		PasswordField passwordField = new PasswordField("Password: ");
		
		HorizontalLayout buttonsContainer = new HorizontalLayout();
		buttonsContainer.setSpacing(true);
		
		Button saveButton = new Button("Save");
		saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveButton.addClickListener(e -> {
			if(!usernameField.isEmpty() && !passwordField.isEmpty()) {
				UserEntity newUser = new UserEntity();
				newUser.setName(usernameField.getValue());
				newUser.setPassword(passwordField.getValue());
				if(isUserAlreadyExists(usernameField.getValue())) {
					usernameField.setComponentError(new UserError("Username is already used!"));
				}
				else {
					userSvc.addUser(newUser);
					this.close();
				}
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelButton.addClickListener(e -> this.close());
		
		buttonsContainer.addComponents(saveButton, cancelButton);
		
		dialogContent.addComponents(usernameField, passwordField, buttonsContainer);
		
		return dialogContent;
	}
	
	private boolean isUserAlreadyExists(String username) {
		return users.stream().anyMatch(user -> user.getName().equals(username));
	}

}
