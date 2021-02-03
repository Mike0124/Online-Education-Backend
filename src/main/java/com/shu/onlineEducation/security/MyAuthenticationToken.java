package com.shu.onlineEducation.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄悦麒
 * @className MyAuthenticationToken
 * @description 认证token
 * @date 2021/1/29
 */
@Slf4j
public class MyAuthenticationToken extends AbstractAuthenticationToken {
	String token;
	SecurityUser securityUser;
	
	//对外部开放的构造方法
	public static MyAuthenticationToken build(SecurityUser securityUser) {
		return new MyAuthenticationToken(buildGrantedAuthority(securityUser.getRoles()), securityUser);
	}
	
	//构建已认证的权限列表
	private static List<GrantedAuthority> buildGrantedAuthority(List<String> roles) {
		List<GrantedAuthority> list = new ArrayList<>(roles.size());
		for (String role : roles) {
			list.add(new SimpleGrantedAuthority(role));
		}
		return list;
	}
	
	//构造方法
	protected MyAuthenticationToken(List<GrantedAuthority> roles, SecurityUser securityUser) {
		super(roles);
		this.token = securityUser.getToken();
		this.securityUser = securityUser;
		this.setAuthenticated(true);
		this.setDetails(securityUser);
	}
	
	@Override
	public Object getCredentials() {
		return token;
	}
	
	@Override
	public Object getPrincipal() {
		return securityUser.getPhoneId();
	}
	
	public String getToken() {
		return token;
	}
	
	public SecurityUser getSecurityUser() {
		return this.securityUser;
	}
	
	public void setSecurityUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}
}
