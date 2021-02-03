package com.shu.onlineEducation.security;

import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author 黄悦麒
 * @className MyAuthenticationProvider
 * @description 提供认证
 * @date 2021/2/2
 */
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		MyJWT myJwt = (MyJWT) authentication;
		SecurityUser securityUser = new SecurityUser();
		//解析jwt
		try {
			securityUser = decode(myJwt);
		} catch (NotFoundException | ParamErrorException notFoundException) {
			notFoundException.printStackTrace();
			throw new DisabledException("用户名密码错误");
		}
		return MyAuthenticationToken.build(securityUser);
	}
	
	public SecurityUser decode(MyJWT myJwt) throws NotFoundException, ParamErrorException {
		return jwtUtil.parseAccessJwtToken(myJwt.getToken());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (authentication.isAssignableFrom(MyJWT.class));
	}
}
