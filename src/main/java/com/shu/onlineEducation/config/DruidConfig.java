package com.shu.onlineEducation.config;


import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 数据库链接池druid监控平台的配置
 * 访问路径 http://localhost:8080/druid/index.html
 */
@Slf4j
@Configuration
public class DruidConfig {
	@Bean
	public ServletRegistrationBean druidServlet() {
		log.info("init Druid Servlet Configuration ");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// IP白名单
//        servletRegistrationBean.addInitParameter("allow", "*");
		// IP黑名单(共同存在时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
		//控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		//是否能够重置数据 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}
}
