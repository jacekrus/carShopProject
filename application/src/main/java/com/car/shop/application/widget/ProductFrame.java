package com.car.shop.application.widget;

import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.service.ShoppingCartService;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ProductFrame extends VerticalLayout {

	private static final long serialVersionUID = 7255583790582103624L;
	
	private ProductEntity product;
	
	public ProductFrame(ProductEntity product, ShoppingCartService cartSvc) {
		this.product = product;
		this.setSpacing(true);
		this.setWidth("136px");
		this.addStyleName("productFrame");
		this.setDescription(product.getName());
		
		Embedded productImg = new Embedded(null, new ThemeResource("img/" + product.getMiniatureImg()));
		
		Button cartButton = new Button();
		cartButton.addStyleName("cartImg");
		cartButton.setWidth("132px");
		cartButton.setHeight("37px");
		cartButton.addClickListener(e -> cartSvc.addProductToCart(product));
		
		Label productName = new Label(product.getName());
		productName.addStyleName("fontBold");
		Label price = new Label(product.getPrice().toString());
		VerticalLayout nameAndPriceContainer = new VerticalLayout(productName, price);
		nameAndPriceContainer.addStyleName("productFrame");
		
		this.addComponents(productImg, nameAndPriceContainer, cartButton);
	}

	public ProductEntity getProduct() {
		return product;
	}
	
}
