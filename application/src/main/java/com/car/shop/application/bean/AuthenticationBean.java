package com.car.shop.application.bean;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.car.shop.application.model.UserEntity;
import com.car.shop.application.service.AuthenticationService;
import com.car.shop.application.utils.AppConstants;

@RequestScoped
public class AuthenticationBean implements AuthenticationService {
	
	@PersistenceContext(unitName = AppConstants.CAR_SHOP_PERSISTENCE_UNIT)
	EntityManager em;

	@Override
	public boolean login(String userName, String password) {
		TypedQuery<UserEntity> query = em.createQuery("select ue from UserEntity ue where ue.name = :userName", UserEntity.class).setParameter("userName", userName);
		try {
			UserEntity result = query.getSingleResult();
			return result == null ? false : authenticateUser(password, result);
		}
		catch (NoResultException  nre) {
			return false;
		}
	}
	
	@Override
	public String encryptPassword(String password) {
		return password;
	}
	
	private boolean authenticateUser(String password, UserEntity user) {
		return user.getPassword().equals(password);
	}

}
