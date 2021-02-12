package com.shu.onlineEducation.security;

import com.shu.onlineEducation.security.MyAuthenticationProvider;
import com.shu.onlineEducation.security.MyAuthenticationToken;
import com.shu.onlineEducation.security.MyJWT;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Order(1)//过滤器级别
@Component
public class MyJwtFilter extends OncePerRequestFilter{
	@Autowired
	MyAuthenticationProvider myAuthenticationProvider;
	
	public MyJwtFilter(MyAuthenticationProvider myAuthenticationProvider) {
		this.myAuthenticationProvider = myAuthenticationProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String authorizationToken = httpServletRequest.getHeader("Authorization");
		//无认证token，不授予权限，放行
		if (authorizationToken == null) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		
		MyJWT jwtString = new MyJWT(authorizationToken);
		try {
			MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken) myAuthenticationProvider.authenticate(jwtString);
			SecurityContextHolder.getContext().setAuthentication(myAuthenticationToken);
		} catch (ExpiredJwtException ex) {//JWT过期
			log.error("Exception caught when decode toke." + ex.getLocalizedMessage());
			((HttpServletResponse) httpServletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		} catch (DisabledException de) {//账户不可用
			log.error("Exception caught when validate account." + de.getLocalizedMessage());
			((HttpServletResponse) httpServletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}

