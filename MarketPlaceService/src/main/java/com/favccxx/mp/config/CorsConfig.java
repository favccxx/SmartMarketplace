package com.favccxx.mp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.favccxx.mp.interceptor.LogInterceptor;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// 放行哪些原始域
				.allowedOrigins("*")
				// 是否发送Cookie信息
				.allowCredentials(true)
				// 放行哪些原始域(请求方式)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
				// 放行哪些原始域(头部信息)
				.allowedHeaders("*").maxAge(3600);		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/api/**");
	}
	
	
}
