package com.car.shop.application.widget;

import com.car.shop.application.callback.EmptyCartCallback;
import com.car.shop.application.service.ShoppingCartService;
import com.car.shop.application.utils.RemoveOption;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class CartItem extends HorizontalLayout {
	
	private static final long serialVersionUID = -1625975583745498375L;

	private ShoppingCartService cartSvc;
	
	private EmptyCartCallback emptyCartCallback;
	
	private Label countLabel;
	
	private Long count;
	
	public CartItem(String productName, Long count, ShoppingCartService cartSvc, EmptyCartCallback emptyCartCallack) {
		this.cartSvc = cartSvc;
		this.emptyCartCallback = emptyCartCallack;
		this.setSpacing(true);
		this.addStyleName("cartItem");
		
		Label name = new Label(productName);
		name.addStyleName("fontBold");
		name.setWidth("120px");
		this.addComponent(name);
		
		Label times = new Label(" X ");
		times.setWidth("20px");
		this.addComponent(times);
		
		this.countLabel = new Label(count.toString());
		this.count = count;
		countLabel.setWidth("20px");
		this.addComponent(countLabel);
		
		HorizontalLayout buttonsContainer = new HorizontalLayout();
		buttonsContainer.setSpacing(true);
		
		Button removeSingleItemButton = new Button("-          ");
		removeSingleItemButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		removeSingleItemButton.setHeight("25px");
		removeSingleItemButton.setWidth("25px");
		removeSingleItemButton.addClickListener(e -> removeSingleItem(productName));
		
		Button removeWholeItemButton = new Button();
		removeWholeItemButton.setCaption("X          ");
		removeWholeItemButton.addStyleName(ValoTheme.BUTTON_DANGER);
		removeWholeItemButton.setHeight("25px");
		removeWholeItemButton.setWidth("25px");
		removeWholeItemButton.addClickListener(e -> removeWholeItem(productName));
		buttonsContainer.addComponents(removeSingleItemButton, removeWholeItemButton);
		
		this.addComponent(buttonsContainer);
	}
	
	private void removeWholeItem(String productName) {
		cartSvc.removeProductFromCart(productName, RemoveOption.WHOLE);
		this.setVisible(false);
		updateCart();
	}
	
	private void removeSingleItem(String productName) {
		cartSvc.removeProductFromCart(productName, RemoveOption.SINGLE);
		count--;
		countLabel.setValue(count.toString());
		this.setVisible(count > 0);
		updateCart();
	}
	
	private void updateCart() {
		if(cartSvc.isEmpty()) {
			emptyCartCallback.onEmptyCart();
		}
	}

}
