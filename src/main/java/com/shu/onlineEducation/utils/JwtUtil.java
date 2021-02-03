package com.shu.onlineEducation.utils;

import com.shu.onlineEducation.security.SecurityUser;
import com.shu.onlineEducation.service.SecurityUserService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "online-education.jwt")
public class JwtUtil {
	
	private String secret;
	private long expire;
	private String header;
	
	@Autowired
	SecurityUserService securityUserService;
	
	
	public Claims getClaimByToken(String token) throws ExpiredJwtException {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String generateToken(String phoneId, String password, String type) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		Date nowDate = new Date();
		//过期时间
		Date expireDate = new Date(nowDate.getTime() + expire * 1000);
		
		//添加构成JWT的参数
		return Jwts.builder().setHeaderParam("typ", "JWT")
				.claim("phone", phoneId)
				.claim("password", password)
				.claim("type", type)
				.signWith(signatureAlgorithm, secret)
				.setIssuedAt(nowDate)
				.setExpiration(expireDate)
				.compact();
	}
	
	public SecurityUser parseAccessJwtToken(String token) throws NotFoundException, ParamErrorException {
		Claims claims = getClaimByToken(token);
		log.info(claims.toString());
		SecurityUser securityUser = new SecurityUser();
		securityUser.setPhoneId((String) claims.get("phone"));
		securityUser.setPassword((String) claims.get("password"));
		securityUser.setType((String) claims.get("type"));
		securityUser.setToken(token);
		securityUserService.authenticateAndAuthorize(securityUser);
		log.info(securityUser.getRoles().toString());
		return securityUser;
	}
}