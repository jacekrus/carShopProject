package com.car.shop.application.interceptor;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@LifecycleLogged
public class BeanLifecycleInterceptor implements Serializable {
	
	private static final long serialVersionUID = -1211791898003640220L;
	
	public BeanLifecycleInterceptor() {}
	
	@PostConstruct
	public void logCreation(InvocationContext ctx) throws Exception {
		System.out.println(getBeanName(ctx) + " CREATED");
		ctx.proceed();
	}
	
	@PreDestroy
	public void logDestruction(InvocationContext ctx) throws Exception {
		System.out.println(getBeanName(ctx) + " DESTROYED");
		ctx.proceed();
	}
	
	private String getBeanName(InvocationContext ctx) {
		String name = ctx.getTarget().getClass().getSimpleName();
		return name.contains("$") ? name.substring(0, name.indexOf("$")) : name;
	}
	
}
