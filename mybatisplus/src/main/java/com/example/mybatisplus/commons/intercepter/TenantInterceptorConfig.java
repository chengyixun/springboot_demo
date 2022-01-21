package com.example.mybatisplus.commons.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: TenantInterceptorConfig @Author: amy @Description:
 *             TenantInterceptorConfig @Date: 2021/10/10 @Version: 1.0
 */
@Slf4j
@Configuration
public class TenantInterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TenantInterceptor()).addPathPatterns("/**");
	}
}
