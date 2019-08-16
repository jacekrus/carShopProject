package com.car.shop.application.view;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.car.shop.application.model.UserEntity;
import com.car.shop.application.notification.AbstractNotification;
import com.car.shop.application.notification.Broadcaster;
import com.car.shop.application.notification.Broadcaster.BroadcastListener;
import com.car.shop.application.notification.UserAddedNotification;
import com.car.shop.application.notification.UserChangedNotification;
import com.car.shop.application.notification.UserChangedNotification.UserChangedNotificationVisitor;
import com.car.shop.application.notification.UserDeletedNotification;
import com.car.shop.application.service.UserService;
import com.car.shop.application.utils.AppConstants;
import com.car.shop.application.utils.NavigationNames;
import com.car.shop.application.widget.AddUserDialog;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(NavigationNames.ADMIN_VIEW)
@StyleSheet(AppConstants.STYLESHEET)
public class AdminView extends CustomComponent implements View, BroadcastListener {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userSvc;
	
	private Button removeUserButton;
	
	private Grid<UserEntity> usersGrid;
	
	private List<UserEntity> users;
	
	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addStyleName("marginLeft");
        
        this.usersGrid = setUpUsersGrid();
        
        VerticalLayout gridContainer = new VerticalLayout();
        gridContainer.setSpacing(true);
        
        HorizontalLayout buttonsContainer = new HorizontalLayout();
        buttonsContainer.setSpacing(true);
        Button addUserButton = new Button("Add user");
        addUserButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addUserButton.addClickListener(e -> showAddUserDialog());

        this.removeUserButton = new Button("Remove user");
        removeUserButton.addStyleName(ValoTheme.BUTTON_DANGER);
        removeUserButton.setEnabled(false);
        removeUserButton.addClickListener(e -> usersGrid.getSelectedItems().forEach(userSvc::removeUser));
        
        buttonsContainer.addComponents(addUserButton, removeUserButton);
        
        gridContainer.addComponents(buttonsContainer, usersGrid);
        layout.addComponent(gridContainer);
        
        setSizeFull();
        setCompositionRoot(layout);
	}
	
	@Override
	public void attach() {
		super.attach();
		Broadcaster.register(this);
	}
	
	@Override
	public void detach() {
		super.detach();
		Broadcaster.unregister(this);
	}
	
	@Override
	public void receiveBroadcast(AbstractNotification notification) {
		getUI().access(() -> {
			if (notification instanceof UserChangedNotification) {
				updateUsersGrid((UserChangedNotification) notification);
			}
		});
	}

	private Grid<UserEntity> setUpUsersGrid() {
		Grid<UserEntity> usersGrid = new Grid<>();
		users = userSvc.findAllUsers();
		usersGrid.setItems(users);
		usersGrid.addColumn(UserEntity::getId).setCaption("ID");
		usersGrid.addColumn(UserEntity::getName).setCaption("Name");
		usersGrid.addColumn(UserEntity::getPassword).setCaption("Password");
		usersGrid.addSelectionListener(new SelectionListener<UserEntity>() {
			
			private static final long serialVersionUID = -8706702694926940770L;

			@Override
			public void selectionChange(SelectionEvent<UserEntity> event) {
				Set<UserEntity> selectedItems = event.getAllSelectedItems();
				if(!selectedItems.isEmpty() && !selectionContainsAdmin(selectedItems)) {
					removeUserButton.setEnabled(true);
				}
				else {
					removeUserButton.setEnabled(false);
				}
			}
		});
		return usersGrid;
	}
	
	private void showAddUserDialog() {
		@SuppressWarnings("unchecked")
		ListDataProvider<UserEntity> dataProvider = (ListDataProvider<UserEntity>) usersGrid.getDataProvider();
		AddUserDialog addUserDialog = new AddUserDialog(userSvc, dataProvider.getItems());
		getUI().addWindow(addUserDialog);
		addUserDialog.focus();
	}
	
	private void updateUsersGrid(UserChangedNotification notification) {
		getUI().access(() -> {
			notification.accept(new UserChangedNotificationVisitor<Void>() {

				@Override
				public Void visit(UserAddedNotification notification) {
					UserEntity user = new UserEntity();
					user.setId(notification.getUserId());
					user.setName(notification.getName());
					user.setPassword(notification.getPassword());
					users.add(user);
					usersGrid.getDataProvider().refreshAll();
					return null;
				}

				@Override
				public Void visit(UserDeletedNotification notification) {
					users.removeIf(usr -> usr.getId().equals(notification.getUserId()));
					usersGrid.getDataProvider().refreshAll();
					return null;
				}
			});
			usersGrid.recalculateColumnWidths();
		});
	}
	
	private boolean selectionContainsAdmin(Set<UserEntity> selectedItems) {
		return selectedItems.stream().filter(item -> item.getId().equals(1L)).findFirst().isPresent();
	}
	
}
