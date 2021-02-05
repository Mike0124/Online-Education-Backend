package com.shu.onlineEducation.utils.idempotent;

import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 接口幂等性拦截器
 */
@Slf4j
@Component
@Order(1)
public class IdempotentInterceptor implements HandlerInterceptor {
	
	
	@Autowired
	private IdempotentToken idempotentToken;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NotFoundException {
		//TODO：不生效的的拦截器
		log.info("doInterceptor");
		//不是控制器方法，放行
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Idempotent idempotent = handlerMethod.getMethod().getAnnotation(Idempotent.class);
		
		if (idempotent != null) {
			//TODO:校验带注解的控制器方法
//			try {
//				return // 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并统一异常处理
//			} catch (NotFoundException notFoundException) {
//				notFoundException.printStackTrace();
//				return false;
//			}
			idempotentToken.verifyToken(request);
			
		}
		return true;//方法无幂等性注解，放行
	}
	
	
}