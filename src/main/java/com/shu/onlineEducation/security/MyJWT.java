package com.shu.onlineEducation.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class MyJWT extends AbstractAuthenticationToken {
	String token;
	
	public MyJWT(String token) {
		super(Collections.emptyList());
		this.token = token;
	}
	
	@Override
	public void setDetails(Object details) {
		super.setDetails(details);
		this.setAuthenticated(true);
	}
	
	@Override
	public Object getCredentials() {
		throw new IllegalStateException();
	}
	
	@Override
	public Object getPrincipal() {
		throw new IllegalStateException();
	}
	
	public String getToken() {
		return token;
	}
}
