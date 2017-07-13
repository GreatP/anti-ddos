package com.cetc.backend.service;


import com.cetc.security.ddos.persistence.service.SecuredUser;
import com.cetc.security.ddos.persistence.UserEntity;
import com.cetc.security.ddos.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserContext {
	
	@Autowired
	private UserService userService;

	/**
	 * Get current user object from context.
	 * 
	 * @return current user;
	 */
	public UserEntity getCurrentUser() {
		
		SecuredUser securedUser = getCurrentSecuredUser();
        if (securedUser == null) {
            return null;
        }
		// Always use the new user information from DB. We have cache at the
		// service level, don't worry about performance.
		// return userService.getUserById(securedUser.getUser().getUserId());
		// db may don't has the user record
		UserEntity user = userService.getUser(securedUser.getUser().getUsername());
		if (user == null) {
			user = securedUser.getUser();
		} else {
			// update user object,because it may be updated by admin or others
			securedUser.setUser(user);
		}
		return user;
	}
	
	public SecuredUser getCurrentSecuredUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth == null) {
			return null;
		}
		Object obj = auth.getPrincipal();
		if (!(obj instanceof SecuredUser)) {
			return null;
		}
		return (SecuredUser) obj;
	}
}
