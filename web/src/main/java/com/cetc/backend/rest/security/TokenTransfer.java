package com.cetc.backend.rest.security;

public class TokenTransfer implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110291825621895299L;
	private final String token;


	public TokenTransfer(String token)
	{
		this.token = token;
	}


	public String getToken()
	{
		return this.token;
	}

}