package com.shu.onlineEducation.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Order(1)//过滤器级别
@Component
public class MyJwtFilter implements Filter {
	@Autowired
	MyAuthenticationProvider myAuthenticationProvider;
	
	public MyJwtFilter(MyAuthenticationProvider myAuthenticationProvider) {
		this.myAuthenticationProvider = myAuthenticationProvider;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorizationToken = httpRequest.getHeader("Authorization");
		//无认证token，不授予权限，放行
		if (authorizationToken == null) {
			chain.doFilter(request, response);
			return;
		}
		
		MyJWT jwtString = new MyJWT(authorizationToken);
		try {
			MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken) myAuthenticationProvider.authenticate(jwtString);
			SecurityContextHolder.getContext().setAuthentication(myAuthenticationToken);
		} catch (ExpiredJwtException ex) {//JWT过期
			log.error("Exception caught when decode toke." + ex.getLocalizedMessage());
			((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		} catch (DisabledException de) {//账户不可用
			log.error("Exception caught when validate account." + de.getLocalizedMessage());
			((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		chain.doFilter(request, response);
	}
}

