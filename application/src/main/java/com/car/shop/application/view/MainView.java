package com.car.shop.application.view;

import com.car.shop.application.utils.LoginUtils;
import com.car.shop.application.utils.NavigationNames;
import com.car.shop.application.widget.SideMenu;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class MainView extends CustomComponent {
	
	private static final long serialVersionUID = 1464389254267017629L;

	private CDIViewProvider viewProvider;
	
	private HorizontalLayout parentLayout;
	
	public MainView(CDIViewProvider viewProvider, HorizontalLayout parentLayout) {
		this.viewProvider = viewProvider;
		this.parentLayout = parentLayout;
		init();
	}

	private void init() {
		SideMenu sideMenu = new SideMenu();
		
		HorizontalLayout viewContainer = new HorizontalLayout();
		viewContainer.setSizeFull();
		viewContainer.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		parentLayout.addComponents(sideMenu, viewContainer);
		parentLayout.setExpandRatio(sideMenu, 0.15F);
		parentLayout.setExpandRatio(viewContainer, 0.75F);
		
		setUpNavigation(viewContainer);
	}
	
	private void setUpNavigation(Layout container) {
		Navigator navigator = new Navigator(UI.getCurrent(), container);
		navigator.addProvider(viewProvider);
		navigator.navigateTo(NavigationNames.DEFAULT);
		navigator.addViewChangeListener(evt -> {
			if(evt.getNewView() instanceof AdminView && !LoginUtils.getLoggedInUsername().equals("admin")) {
				LoginUtils.showNotification("You don't have permission to access this view.", Type.ERROR_MESSAGE, Position.MIDDLE_CENTER);
				return false;
			}
			return true;
		});
	}
	
}
