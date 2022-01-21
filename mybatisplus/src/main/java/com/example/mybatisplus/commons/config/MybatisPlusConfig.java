package com.example.mybatisplus.commons.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.mybatisplus.commons.handler.AuditMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu @Date: Created 2020-12-08 23:01 @Description: @Modified By:
 */
@Configuration
@EnableTransactionManagement
@MapperScan({ "com.example.mybatisplus.mapper" })
@Slf4j
public class MybatisPlusConfig {

	/**
	 * 审计数据插件
	 *
	 * @return
	 */
	@Bean
	public AuditMetaObjectHandler auditMetaObjectHandler() {
		return new AuditMetaObjectHandler();
	}

	@Bean("master")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource master() {
		log.info("==== bean DataSource");
		return DataSourceBuilder.create().build();
	}

	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put("master", master());
		// 将 master 数据源作为默认指定的数据源
		dynamicDataSource.setDefaultDataSource(master());
		// 将 master 数据源作为指定的数据源
		dynamicDataSource.setDataSources(dataSourceMap);
		log.info("==== bean dynamicDataSource");
		return dynamicDataSource;
	}

	@Bean
	public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
		/** 重点，使分页插件生效 */
		Interceptor[] plugins = new Interceptor[1];
		plugins[0] = paginationInterceptor();
		sessionFactory.setPlugins(plugins);
		// 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
		sessionFactory.setDataSource(dynamicDataSource());
		// 扫描Model
		sessionFactory.setTypeAliasesPackage("com.example.mybatisplus.entity");
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// 扫描映射文件
		sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		// 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
		return new DataSourceTransactionManager(dynamicDataSource());
	}

	/**
	 * 加载分页插件
	 *
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

		List<ISqlParser> sqlParserList = new ArrayList<>();
		// 攻击 SQL 阻断解析器、加入解析链
		sqlParserList.add(new BlockAttackSqlParser());
		paginationInterceptor.setSqlParserList(sqlParserList);
		return paginationInterceptor;
	}

	/**
	 * 新版本mp 1.分页 2.乐观锁
	 *
	 * @return
	 */
	/*
	 * @Bean public MybatisPlusInterceptor mybatisPlusInterceptor() {
	 * MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
	 * interceptor.addInnerInterceptor(new
	 * PaginationInnerInterceptor(DbType.MYSQL));
	 * interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
	 * return interceptor; }
	 */
}
