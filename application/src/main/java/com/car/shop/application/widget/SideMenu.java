package com.car.shop.application.widget;

import com.car.shop.application.utils.LoginUtils;
import com.car.shop.application.utils.NavigationNames;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SideMenu extends CssLayout {
	
	private static final long serialVersionUID = 8324709472745473158L;

	private VerticalLayout componentContainer;
	
	private Button logoutButton;
	
	public SideMenu() {
		this.setWidth("100%");
		this.setHeight("100%");
		this.addStyleName(ValoTheme.MENU_ROOT);
		this.addStyleName("sideMenu");
		init();
	}
	
	private void init() {
		componentContainer = new VerticalLayout();
		componentContainer.setWidth("100%");
		componentContainer.setHeight("100%");
		componentContainer.addComponent(initMenuTitle()); 
		VerticalLayout navigationButtons = initNavigationButtons();
		componentContainer.addComponent(navigationButtons);
		logoutButton = setUpLogoutButton();
		componentContainer.addComponent(logoutButton);
		componentContainer.setComponentAlignment(logoutButton, Alignment.MIDDLE_CENTER);
		componentContainer.setExpandRatio(navigationButtons, 0.4F);
		componentContainer.setExpandRatio(logoutButton, 0.1F);
		componentContainer.addStyleName("sideMenu");
		this.addComponent(componentContainer);
	}
	
	private Label initMenuTitle() {
		Label title = new Label("Menu");
		title.setWidth("100%");
		title.addStyleName(ValoTheme.MENU_TITLE);
		return title;
	}
	
	private VerticalLayout initNavigationButtons() {
		VerticalLayout menuButtons = new VerticalLayout();
		menuButtons.setSpacing(true);
		menuButtons.setMargin(true);
		if("admin".equals(LoginUtils.getLoggedInUsername())) {
			Button adminButton = setUpMenuButton("Admin", e -> UI.getCurrent().getNavigator().navigateTo(NavigationNames.ADMIN_VIEW));
			menuButtons.addComponent(adminButton);
		}
		Button shopButton = setUpMenuButton("Shop", e -> UI.getCurrent().getNavigator().navigateTo(NavigationNames.SHOP_VIEW));
		Button statisticsButton = setUpMenuButton("Statistics", e -> UI.getCurrent().getNavigator().navigateTo(NavigationNames.STATISTICS_VIEW));
		menuButtons.addComponents(shopButton, statisticsButton);
		return menuButtons;
	}
	
	private Button setUpMenuButton(String caption, ClickListener listener) {
		Button menuButton = new Button(caption, listener);
		menuButton.setDescription(caption);
		menuButton.setWidth("100%");
		return menuButton;
	}
	
	private Button setUpLogoutButton() {
		Button logout = new Button();
		logout.addStyleName("logoutButton");
		logout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logout.setWidth("55px");
		logout.setHeight("55px");
		logout.setDescription("Logout");
		logout.addClickListener(e -> doLogout());
		return logout;
	}
	
	private void doLogout() {
		UI currentUI = getUI();
		VaadinSession.getCurrent().getUIs().forEach(ui -> {
			if (!ui.equals(currentUI)) {
				ui.access(() -> {
					ui.getSession().close();
					ui.close();
				});
			}
		});
		currentUI.getPage().open("", "_self");
		currentUI.close();
		currentUI.getSession().close();
		currentUI.getPushConnection().disconnect();
		currentUI.getSession().getSession().invalidate();
	}
	
}
