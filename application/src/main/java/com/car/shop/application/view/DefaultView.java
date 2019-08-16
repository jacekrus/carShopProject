package com.car.shop.application.view;

import com.car.shop.application.utils.AppConstants;
import com.car.shop.application.utils.NavigationNames;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;

@CDIView(NavigationNames.DEFAULT)
@StyleSheet(AppConstants.STYLESHEET)
public class DefaultView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setSizeFull();
		
        Label label = new Label("THE WORLD'S LARGEST CAR EQUIPMENT SITE");
		label.addStyleName("specialFont");
		Embedded logo = new Embedded(null, new ThemeResource("img/newLogo.png"));
		
		layout.addComponent(label, "left: 60px; top: 100px");
        layout.addComponent(logo, "left: 0px; top: 50px");
        setSizeFull();
        setCompositionRoot(layout);
	}

}
