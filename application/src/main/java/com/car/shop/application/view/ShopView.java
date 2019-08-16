package com.car.shop.application.view;

import java.util.Map;

import javax.inject.Inject;

import com.car.shop.application.service.ProductService;
import com.car.shop.application.service.ShoppingCartService;
import com.car.shop.application.utils.AppConstants;
import com.car.shop.application.utils.NavigationNames;
import com.car.shop.application.widget.CartItem;
import com.car.shop.application.widget.ProductFrame;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@CDIView(NavigationNames.SHOP_VIEW)
@StyleSheet(AppConstants.STYLESHEET)
public class ShopView extends CustomComponent implements View {

	private static final long serialVersionUID = -7480398079308043714L;
	
	@Inject
	private ProductService prodSvc;
	
	@Inject
	private ShoppingCartService cartSvc;

	@Override
	public void enter(ViewChangeEvent event) {
		AbsoluteLayout layout = new AbsoluteLayout();
		layout.setSizeFull();
		
		GridLayout productsGrid = new GridLayout(3,2);
		productsGrid.setSpacing(true);
		productsGrid.setMargin(true);
		productsGrid.setWidth("80%");
		
		Button cartButton = setUpShoppingCartButton();
		
		prodSvc.findAllProducts().forEach(product -> productsGrid.addComponent(new ProductFrame(product, cartSvc)));
		
		layout.addComponent(productsGrid, "left: 150px; top: 50px");
		layout.addComponent(cartButton, "right: 20px; top: 20px");
		
		setSizeFull();
		setCompositionRoot(layout);
	}
	
	private Button setUpShoppingCartButton() {
		Button cartButton = new Button();
		cartButton.addStyleName("cartIcon");
		cartButton.setWidth("60px");
		cartButton.setHeight("60px");
		cartButton.addClickListener(e -> showCartContent());
		return cartButton;
	}
	
	private void showCartContent() {
		Map<String, Long> productsInCart = cartSvc.getProductsInCart();
		
		VerticalLayout dialogContent = new VerticalLayout();
		dialogContent.setSpacing(true);
		dialogContent.setWidth("100%");
		
		Label emptyCart = new Label("Cart is empty");
		emptyCart.addStyleName("fontBold");
		emptyCart.addStyleName("cartItem");
		emptyCart.setVisible(false);
		dialogContent.addComponent(emptyCart);
		
		if(productsInCart.isEmpty()) {
			emptyCart.setVisible(true);
		}
		else {
			productsInCart.entrySet().forEach(entry -> 
				dialogContent.addComponent(new CartItem(entry.getKey(), entry.getValue(), cartSvc, () -> emptyCart.setVisible(true))));
		}
		
		Window cartDialog = new Window("Your cart's content ");
		cartDialog.setWidth("330px");
		cartDialog.setContent(dialogContent);
		cartDialog.center();
		cartDialog.setResizable(false);
		cartDialog.setModal(true);
		
		UI.getCurrent().addWindow(cartDialog);
		cartDialog.focus();
	}
	
}
