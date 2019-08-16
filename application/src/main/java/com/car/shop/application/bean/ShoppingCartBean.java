package com.car.shop.application.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import com.car.shop.application.interceptor.LifecycleLogged;
import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.service.ShoppingCartService;
import com.car.shop.application.utils.RemoveOption;
import com.car.shop.application.utils.RemoveOption.RemoveOptionVisitor;

@SessionScoped
@LifecycleLogged
public class ShoppingCartBean implements ShoppingCartService, Serializable {

	private static final long serialVersionUID = -7322158913457105616L;
	
	private Map<String, Long> productsInCart;
	
	public ShoppingCartBean() {}
	
	@PostConstruct
	private void init() {
		productsInCart = new HashMap<>();
	}
	
	@Override
	public Map<String, Long> getProductsInCart() {
		return productsInCart;
	}
	
	@Override
	public void addProductToCart(ProductEntity product) {
		if(productsInCart.get(product.getName()) == null) {
			productsInCart.put(product.getName(), 1L);
		}
		else {
			productsInCart.put(product.getName(), (productsInCart.get(product.getName()) + 1));
		}
	}

	@Override
	public void removeProductFromCart(String productName, RemoveOption option) {
		if(productsInCart.containsKey(productName)) { 
			option.accept(new RemoveOptionVisitor<Void>() {
				@Override
				public Void visitWhole() {
					productsInCart.remove(productName);
					return null;
				}

				@Override
				public Void visitSingle() {
					Long newCount = productsInCart.get(productName) - 1;
					if(newCount > 0) {
						productsInCart.put(productName, newCount);
					}
					else {
						productsInCart.remove(productName);
					}
					return null;
				}
			});
		}
	}

	@Override
	public boolean isEmpty() {
		return productsInCart.isEmpty();
	}
	
}
