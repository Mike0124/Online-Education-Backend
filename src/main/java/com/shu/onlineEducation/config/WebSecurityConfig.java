package com.shu.onlineEducation.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Configuration
public class WebSecurityConfig {
	
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(
			prePostEnabled = true,
			securedEnabled = true,
			jsr250Enabled = true)
	public static class LocalSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			super.configure(web);
			web.ignoring().antMatchers("/static/**");
			
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = this.authenticationManager();
			
			http.headers().cacheControl().and().frameOptions().disable()
					.and()
					.cors()
					.and()
					.csrf().disable()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//无状态session
					.and()
					.authorizeRequests()
					.antMatchers("/swagger-ui.html").permitAll() // Protected swagger-ui End-points
					.and()
					.authorizeRequests()
					.antMatchers("/api/**").permitAll() // Protected API End-points
					.and()
					.authorizeRequests()
					.antMatchers("/websocket/**").permitAll() // Protected WebSocket End-points
					.and()
					.exceptionHandling().accessDeniedHandler(new SampleAccessDeniedHandler());
			
		}
	}
	
	static class SampleAccessDeniedHandler implements AccessDeniedHandler {
		
		@Override
		public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
			log.error(e.getMessage());
			httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}
}

