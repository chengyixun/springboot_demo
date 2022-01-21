package com.example.mybatispluscrud.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: MybatisPlusConfig @Author: amy @Description:
 *             MybatisPlusConfig @Date: 2021/11/15 @Version: 1.0
 */
@Slf4j
@EnableTransactionManagement // 开启事务
@Configuration
@MapperScan("com.example.mybatispluscrud.mapper")
public class MybatisPlusConfig {
	/**
	 * 新版本mp分页
	 *
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 乐观锁配置
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		// 分页配置
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
}
