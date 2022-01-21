package com.example.admin.commons.componet;

import com.example.admin.commons.annotation.PermissionOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@link PermApplication} 启动前初始化的内容
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-12
 */
@Slf4j
@Component
public class PermApplication implements ApplicationContextAware, CommandLineRunner {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 获取所有的controller, 通过aop获取类，再获取方法，最后获取方法上的注解信息
	 *
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		// 获取使用RestController注解标注的的所有controller类
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);

		// 遍历 获取controller层对应的方法上的注解对应的信息
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object classValue = entry.getValue();
			Class<?> aClass = AopUtils.getTargetClass(classValue);
			RequestMapping requestMappingAnn = aClass.getAnnotation(RequestMapping.class);
			log.info("controller request url:{}", requestMappingAnn.value());
			Method[] methods = aClass.getMethods();
			String[] requestValue = requestMappingAnn.value();
			if (null != methods) {
				for (Method method : methods) {
					PermissionOperation ann = method.getAnnotation(PermissionOperation.class);
					if (null != ann) {
						// 放入缓存或者数据库
						log.info("auth:{},desc:{}", ann.code(), ann.describe());
						// 获取url
						for (String s : requestValue) {
							log.info("url:{}", s);
						}
					}
				}
			}
		}
	}
}
