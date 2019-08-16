package com.car.shop.application.service;

import java.util.List;

import com.car.shop.application.model.ProductEntity;

public interface ProductService {
	
	List<ProductEntity> findAllProducts();

}
