package com.cetc.security.ddos.persistence.service;

/**
 * Role of the User.
 * 
 */
public enum Role {

	/**
	 * General user role who can manage his own service.
	 */
	USER("U", "General User"),

    /**
     * Tenant user role who can manage his own service
     */
    TENANT("T", "Tenant"),

	/**
	 * Admin user role who can do everything.
	 */
	ADMIN("A", "Administrator");

	private final String shortName;

	private final String fullName;

	/**
	 * Constructor.
	 * 
	 * @param shortName
	 *            short name of role... usually 1 sing char
	 * @param fullName
	 *            full name of role
	 */
	Role(String shortName, String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
	}

	/**
	 * Get the short name.
	 * 
	 * @return short name
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Get full name.
	 * 
	 * @return full name
	 */
	public String getFullName() {
		return fullName;
	}

	public static Role getByFullName(String fname) {
		if (Role.ADMIN.getFullName().equals(fname)) {
			return Role.ADMIN;
		} else if (Role.USER.getFullName().equals(fname)) {
			return Role.USER;
		} else if (Role.TENANT.getFullName().equals(fname)) {
            return Role.TENANT;
		} else {
            return null;
        }
	}
}
