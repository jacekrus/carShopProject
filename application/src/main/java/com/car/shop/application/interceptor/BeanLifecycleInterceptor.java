package com.car.shop.application.interceptor;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

@Interceptor
@LifecycleLogged
public class BeanLifecycleInterceptor implements Serializable {
	
	private static final long serialVersionUID = -1211791898003640220L;
	
	private static Logger logger = Logger.getLogger("Bean Lifecycle");
	
	public BeanLifecycleInterceptor() {}
	
	@PostConstruct
	public void logCreation(InvocationContext ctx) throws Exception {
		logger.info(getBeanName(ctx) + " CREATED \n");
		ctx.proceed();
	}
	
	@PreDestroy
	public void logDestruction(InvocationContext ctx) throws Exception {
		logger.info(getBeanName(ctx) + " DESTROYED \n");
		ctx.proceed();
	}
	
	private String getBeanName(InvocationContext ctx) {
		String name = ctx.getTarget().getClass().getSimpleName();
		return name.contains("$") ? name.substring(0, name.indexOf("$")) : name;
	}
	
}
