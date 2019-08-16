package com.car.shop.application.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.car.shop.application.model.ProductEntity;
import com.car.shop.application.model.UserEntity;
import com.car.shop.application.service.DataAccessService;
import com.car.shop.application.service.StatisticsService;
import com.car.shop.application.utils.AppConstants;

@Stateless
public class DataAccessBean implements DataAccessService {
	
	@PersistenceContext(unitName = AppConstants.CAR_SHOP_PERSISTENCE_UNIT)
	private EntityManager em;
	
	@Inject
	private StatisticsService statSvc;

	@Override
	public void persist(Object obj) {
		em.persist(obj);
		statSvc.incrementDBConnectionsNumber();
	}

	@Override
	public List<UserEntity> findAllUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> root = cq.from(UserEntity.class);
		CriteriaQuery<UserEntity> query = cq.select(root);
		TypedQuery<UserEntity> createQuery = em.createQuery(query);
		statSvc.incrementDBConnectionsNumber();
		return createQuery.getResultList();
	}

	@Override
	public List<ProductEntity> findAllProducts() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> root = cq.from(ProductEntity.class);
		CriteriaQuery<ProductEntity> query = cq.select(root);
		TypedQuery<ProductEntity> createQuery = em.createQuery(query);
		statSvc.incrementDBConnectionsNumber();
		return createQuery.getResultList();
	}

	@Override
	public void remove(Object obj) {
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		statSvc.incrementDBConnectionsNumber();
	}

}
