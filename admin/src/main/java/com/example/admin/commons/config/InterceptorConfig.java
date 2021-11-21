package com.example.admin.commons.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.admin.commons.interceptor.AccessLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * {@link InterceptorConfig}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-06
 */
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessLogInterceptor()).addPathPatterns("/**");
		// registry.addInterceptor(apiIdempotentInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public AccessLogInterceptor accessLogInterceptor() {
		return new AccessLogInterceptor();
	}

	/**
	 * 配置消息转换器，使用alibaba的fastjson
	 *
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		// Converter转换器
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		// 配置项
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				// 输出空置字段
				// SerializerFeature.WriteMapNullValue,
				// 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect,
				// list字段如果为null，输出为[]，而不是null
				SerializerFeature.WriteNullListAsEmpty,

				SerializerFeature.WriteDateUseDateFormat);
		// 时间格式
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

		// 4.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

		converters.add(0, fastJsonHttpMessageConverter);

	}
}
