package com.car.shop.application.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.car.shop.application.qualifier.Encrypted;
import com.car.shop.application.service.AuthenticationService;

@ApplicationScoped
public class AuthenticationServiceProvider {
	
	@Inject
//	@Any
	private Instance<AuthenticationService> authSvcInstances;
	
	private AuthenticationService authSvc;
	
	@PostConstruct
	private void init() {
		if(authSvcInstances.isAmbiguous()) {
			authSvc = authSvcInstances.select(new AnnotationLiteral<Encrypted>() {
				private static final long serialVersionUID = 1266877314899977342L;
			}).get();
		}
		else {
			authSvc = authSvcInstances.get();
		}
	}
	
	public AuthenticationService getAuthSvc() {
		return authSvc;
	}

}
