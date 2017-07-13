package com.cetc.backend.rest.security;

import java.util.List;


public class UserTransfer
{

	private final String name;

	private final List<String> roles;


	public UserTransfer(String userName, List<String> roles)
	{
		this.name = userName;
		this.roles = roles;
	}


	public String getName()
	{
		return this.name;
	}


	public List<String> getRoles()
	{
		return this.roles;
	}

}