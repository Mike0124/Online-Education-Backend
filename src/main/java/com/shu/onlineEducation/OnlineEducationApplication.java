package com.shu.onlineEducation;

import com.shu.onlineEducation.security.MyJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class OnlineEducationApplication {
	@Autowired
	MyJwtFilter myJwtFilter;
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineEducationApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<MyJwtFilter> tokenFilter() {
		FilterRegistrationBean<MyJwtFilter> registrationBean
				= new FilterRegistrationBean<>();
		
		registrationBean.setFilter(myJwtFilter);
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
}
