package com.cetc.backend.rest.security;

import java.io.Serializable;

import com.cetc.security.ddos.persistence.service.SecuredUser;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;


public class CustomPermissionEvaluator implements PermissionEvaluator {
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission){
		
		Object principal = authentication.getPrincipal();
		SecuredUser user = null;
		if(principal instanceof SecuredUser){
			String roleKey = (String)permission;
			user = (SecuredUser)principal;
			return user.hasRole(roleKey);
		}else{
			return false;
		}
		
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission){
		throw new UnsupportedOperationException();
	}
	
}
