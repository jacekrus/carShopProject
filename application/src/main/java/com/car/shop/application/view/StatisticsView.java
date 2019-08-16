package com.car.shop.application.view;

import javax.inject.Inject;

import com.car.shop.application.service.StatisticsService;
import com.car.shop.application.utils.AppConstants;
import com.car.shop.application.utils.NavigationNames;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView(NavigationNames.STATISTICS_VIEW)
@StyleSheet(AppConstants.STYLESHEET)
public class StatisticsView extends CustomComponent implements View{

	private static final long serialVersionUID = 5164669140804274162L;
	
	@Inject
	private StatisticsService statSvc;

	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        
        Label dbConnectionsPerUserLabel = new Label("Number of DB connections for current user: " + statSvc.getNumberOfDBConnectionsForCurrentUser());
        Label allDBConnectionsLabel = new Label("Total number of DB connections since " + statSvc.getCreationDate() + ": " + statSvc.getNumberOfAllDBConnections());
        
        layout.addComponents(dbConnectionsPerUserLabel, allDBConnectionsLabel);
        
        setSizeFull();
        setCompositionRoot(layout);
	}

}
