package com.example.mybatispluscrud.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.mybatispluscrud.common.constants.SqlConstants.CREATE_TIME;
import static com.example.mybatispluscrud.common.constants.SqlConstants.UPDATE_TIME;

/**
 * @ClassName: CustomMetaObjectHandler @Author: amy @Description:
 *             CustomMetaObjectHandler @Date: 2021/11/15 @Version: 1.0
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert field....");
		this.setFieldValByName(CREATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
	}
}
