package com.car.shop.application.service;

import java.util.Map;

import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.utils.RemoveOption;

public interface ShoppingCartService {
	
	Map<String, Long> getProductsInCart();
	
	void addProductToCart(ProductEntity product);
	
	void removeProductFromCart(String productName, RemoveOption option);
	
	boolean isEmpty();

}
