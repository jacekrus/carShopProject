package com.car.shop.application.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.car.shop.application.model.UserEntity;
import com.car.shop.application.qualifier.Encrypted;
import com.car.shop.application.service.AuthenticationService;
import com.car.shop.application.utils.AppConstants;

@RequestScoped
@Encrypted
public class EncryptedAuthenticationBean implements AuthenticationService {
	
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
		return doEncrypt(password);
	}
	
	private boolean authenticateUser(String password, UserEntity user) {
		return user.getPassword().equals(doEncrypt(password)) || user.getPassword().equals(password);
	}
	
	private String doEncrypt(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = password.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString().substring(0, 9);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}
	
}
