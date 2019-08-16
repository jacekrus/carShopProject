package com.car.shop.application.service;

import java.util.List;

import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.model.UserEntity;

public interface DataAccessService {
	
	void persist(Object obj);
	
	void remove(Object obj);
	
	List<UserEntity> findAllUsers();
	
	List<ProductEntity> findAllProducts();

}
