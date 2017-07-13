
package com.cetc.backend.rest.security;

import com.cetc.backend.service.UserContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cetc.security.ddos.persistence.UserEntity;



public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private UserContext userContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * supportsParameter(org .springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserEntity.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * resolveArgument(org. springframework.core.MethodParameter,
	 * org.springframework.web.method.support.ModelAndViewContainer,
	 * org.springframework.web.context.request.NativeWebRequest,
	 * org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return userContext.getCurrentUser();
	}
	
	/**
	 * Get current user context.<br/>
	 * This method is provided for XML based spring bean injection.
	 * 
	 * @return user context
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * Set the current user context.<br/>
	 * This method is provided for XML based spring bean injection.
	 * 
	 * @param userContext
	 *            user context.
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
}
