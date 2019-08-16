package com.car.shop.application.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.car.shop.application.interceptor.LifecycleLogged;
import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.service.DataAccessService;
import com.car.shop.application.service.ProductService;

@RequestScoped
@LifecycleLogged
public class ProductBean implements ProductService {
	
	@Inject
	DataAccessService dbSvc;
	
	@Override
	public List<ProductEntity> findAllProducts() {
		return dbSvc.findAllProducts();
	}

}
