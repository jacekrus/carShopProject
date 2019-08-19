package com.car.shop.application.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.car.shop.application.event.UserAddedEvent;
import com.car.shop.application.event.UserDeletedEvent;
import com.car.shop.application.interceptor.LifecycleLogged;
import com.car.shop.application.model.UserEntity;
import com.car.shop.application.service.DataAccessService;
import com.car.shop.application.service.UserService;

//@Dependent
//@Stateless
@RequestScoped
@LifecycleLogged
public class UserBean implements UserService {
	
	@Inject
	private DataAccessService dbSvc;
	
	@Inject
	private Event<UserAddedEvent> userAddedEvent;
	
	@Inject
	private Event<UserDeletedEvent> userDeletedEvent;
	
	@Inject
	private AuthenticationServiceProvider authSvcProvider;
	
	@Override
	public void addUser(UserEntity userEntity) {
		userEntity.setPassword(authSvcProvider.getAuthSvc().encryptPassword(userEntity.getPassword()));
		dbSvc.persist(userEntity);
		userAddedEvent.fire(new UserAddedEvent(userEntity.getId(), userEntity.getName(), userEntity.getPassword()));
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return dbSvc.findAllUsers();
	}

	@Override
	public void removeUser(UserEntity userEntity) {
		if(!userEntity.getName().equals("admin")) {
			dbSvc.remove(userEntity);
			userDeletedEvent.fire(new UserDeletedEvent(userEntity.getId()));
		}
	}
	
	@Override
	public UserEntity findUserByName(String username) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
