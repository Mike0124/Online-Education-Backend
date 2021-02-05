package com.shu.onlineEducation.config;


import com.shu.onlineEducation.utils.idempotent.IdempotentInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	//TODO:不清楚是否成功注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new IdempotentInterceptor()).addPathPatterns("/**");//所有路径都被拦截
	}

}
