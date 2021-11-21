package com.example.mybatisplus.commons.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @ClassName: AuditMetaObjectHandler
 * @Author: amy
 * @Description: AuditMetaObjectHandler
 * @Date: 2021/8/21
 * @Version: 1.0
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		boolean createTime = metaObject.hasSetter("createTime");
		boolean updateTime = metaObject.hasSetter("updateTime");
		if (createTime || updateTime) {
			Date now = new Date();
			if (createTime) {
				this.setFieldValByName("createTime", now, metaObject);
			}
			if (updateTime) {
				this.setFieldValByName("updateTime", now, metaObject);
			}
		}

		// 当前登陆用户的获取 threadLocal
		// 或者 如果使用SpringSecurity的话可以从 SecurityContext中获取
		String username = "admin";
		if (metaObject.hasSetter("createUser")) {
			this.setFieldValByName("createUser", username, metaObject);
		}

		if (metaObject.hasSetter("updateUser")) {
			this.setFieldValByName("updateUser", username, metaObject);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (metaObject.hasSetter("updateTime")) {
			this.setFieldValByName("updateTime", new Date(), metaObject);
		}
		if (metaObject.hasSetter("updateUser")) {
			String username = "admin";
			this.setFieldValByName("updateUser", username, metaObject);
		}

	}
}
