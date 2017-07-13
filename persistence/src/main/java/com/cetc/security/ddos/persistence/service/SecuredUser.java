package com.cetc.security.ddos.persistence.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetc.security.ddos.persistence.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.ImmutableList;


public class SecuredUser implements UserDetails {

	private static final long serialVersionUID = 9160341654874660746L;

	private static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");
	private static final GrantedAuthority USER = new SimpleGrantedAuthority("USER");
    private static final GrantedAuthority TENANT = new SimpleGrantedAuthority("TENANT");

	private static final List<GrantedAuthority> ADMIN_LIST = ImmutableList.of(ADMIN, USER, TENANT);
	private static final List<GrantedAuthority> USER_LIST = ImmutableList.of(USER, TENANT);
    private static final List<GrantedAuthority> TENANT_LIST = ImmutableList.of(TENANT);

	private static final Map<String, GrantedAuthority> ROLE_MAP = new HashMap<String, GrantedAuthority>();


	static {
		ROLE_MAP.put("ADMIN", ADMIN);
		ROLE_MAP.put("USER", USER);
        ROLE_MAP.put("TENANT", TENANT);
	}

	/**
	 * Plugin class name from which {@link User} instance is provided.
	 */
	private UserEntity user;

	/**
	 * User instance used for SpringSecurity.
	 * 
	 * @param user
	 *            real user info
	 * @param userInfoProviderClass
	 *            class name who provides the user info
	 */
	public SecuredUser(UserEntity user) {
		this.user = user;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param role
	 * @param enabled
	 * @param external
	 */
	public SecuredUser(String userName, String password, Role role,
			boolean enabled, boolean external) {
		UserEntity user = new UserEntity(); //(userName, userName, password, role);
		user.setUsername(userName);
		user.setPassword(password);
		user.setRole(role);

		this.user = user;

	}

	/**
	 * Return provided authorities. It returns one Role from {@link User} in the
	 * {@link GrantedAuthority} list.
	 * 
	 * @return {@link GrantedAuthority} list
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		switch (user.getRole()) {
			case ADMIN:
				return ADMIN_LIST;
			case USER:
				return USER_LIST;
            case TENANT:
                return TENANT_LIST;
			default:
				break;
		}
		return USER_LIST;
	}

	public boolean hasRole(String roleKey) {
		GrantedAuthority grantRole = ROLE_MAP.get(roleKey);
		return (grantRole != null && getAuthorities().contains(grantRole));
	}

	/**
	 * Return password.
	 * 
	 * @return password
	 */
	@Override
	public String getPassword() {
		return getUser().getPassword();
	}

	/**
	 * used when external user account
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		getUser().setPassword(password);
	}

	/**
	 * Return Username (Actually user id).
	 * 
	 * @return user name
	 */
	@Override
	public String getUsername() {
		return getUser().getUsername();
	}
	

	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}