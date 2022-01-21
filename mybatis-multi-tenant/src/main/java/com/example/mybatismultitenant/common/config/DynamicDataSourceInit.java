package com.example.mybatismultitenant.common.config;

import com.example.mybatismultitenant.common.context.TenantContext;
import com.example.mybatismultitenant.model.Tenant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.mybatismultitenant.constant.CommonConstant.BASE_DATASOURCE_KEY;

/**
 * @ClassName: DynamicDataSourceInit @Author: amy @Description:
 *             DynamicDataSourceInit 用于初始化所有租户的数据源 @Date: 2021/10/10 @Version:
 *             1.0
 */
@Slf4j
@Configuration
public class DynamicDataSourceInit {

	@Autowired
	private BaseDataSourceConfig baseDataSourceConfig;

	@Resource
	private DynamicDataSource dynamicDataSource;

	/** 基础数据源为单例 */
	private static HikariDataSource singleBaseDataSource = null;

	/**
	 * 初始化【基础数据源】的方法
	 *
	 * @return
	 */
	public HikariDataSource getBaseDataSource() {
		if (null != singleBaseDataSource) {
			return singleBaseDataSource;
		}
		HikariConfig jdbcConfig = new HikariConfig();
		jdbcConfig.setDriverClassName(baseDataSourceConfig.getDriverClass());
		jdbcConfig.setJdbcUrl(baseDataSourceConfig.getUrl());
		jdbcConfig.setUsername(baseDataSourceConfig.getUsername());
		jdbcConfig.setPassword(baseDataSourceConfig.getPassword());
		// jdbcConfig.setConnectionTestQuery(baseDataSourceConfig.getValidationQuery());
		// jdbcConfig.setConnectionInitSql(baseDataSourceConfig.getValidationQuery());
		// 设置其他数据源配置属性
		jdbcConfig.setConnectionTimeout(baseDataSourceConfig.getConnectionTimeout());
		jdbcConfig.setMaxLifetime(baseDataSourceConfig.getMaxLifetime());
		jdbcConfig.setIdleTimeout(baseDataSourceConfig.getIdleTimeout());
		jdbcConfig.setMaximumPoolSize(baseDataSourceConfig.getMaximumPoolSize());
		jdbcConfig.setMinimumIdle(baseDataSourceConfig.getMinimumIdle());
		jdbcConfig.setLeakDetectionThreshold(baseDataSourceConfig.getLeakDetectionThreshold());
		HikariDataSource baseDataSource = new HikariDataSource(jdbcConfig);
		singleBaseDataSource = baseDataSource;
		return baseDataSource;
	}

	@PostConstruct
	public void initDataSource() {
		log.info("=====执行InitDataSource方法,初始化动态数据源=====");
		// 1.从Spring上下文中取出动态数据源bean(此时动态数据源内只有一个基础数据源，其键名为baseDataSource)
		// 2.初始化动态数据源dynamicDataSource管理的所有租户数据源(最终所有数据源=1个基础数据源+n个租户数据源)
		Map<Object, Object> dataSourceMap = dynamicDataSource.getTargetDataSources();
		HikariDataSource baseDataSource = (HikariDataSource) dataSourceMap.get(BASE_DATASOURCE_KEY);
		Map<String, Tenant> tenantMap = new HashMap<>(16);
		Connection conn = null;
		// 3.从基础数据源中,获取连接,查出所有租户数据源,然后创建出租户数据源,并放入【动态数据源】维护的总库map中
		try {
			conn = baseDataSource.getConnection();
			// 【防御机制----判断当前是多租户还是单机版--原理:去基础数据源中查找是否存在tenant表】
			// 缓存机制:validateTableNameExist方法执行后,会将判断结果缓存起来,之后调用MultiTenantUtils.isMultiTenant()将直接获取缓存中的结果，避免重复查询数据库
			/*
			 * if (MultiTenantUtils.isMultiTenant() == null) { //
			 * 如果MultiTenantUtils工具类尚未初始化,则在此处初始化一下 boolean isMultiTenantPattern =
			 * validateTableNameExist(conn, "tenant");
			 * MultiTenantUtils.setMultiTenantEnabled(isMultiTenantPattern); }
			 */
			// 从数据库中获取所有租户信息(筛选条件tenantId为空)
			// if (MultiTenantUtils.isMultiTenant()) {
			if (conn.isClosed()) {
				// validateTableNameExist方法内原先的conn会被close,我们再获取一根新的连接
				conn = baseDataSource.getConnection();
			}
			// 获取所有租户信息,tenantId查询条件为null即为查询所有
			List<Tenant> tenantList = getTenant(conn, null);
			// TODO: 2021/10/11 租户配置是否启用 暂时先不考虑
			for (Tenant tenant : tenantList) {
				// 只有【1启用态】的租户，并且【尚未初始化过数据源】的，才会被初始化
				if (null == dataSourceMap.get(tenant.getTenantId())) {
					log.info("初始化租户:" + tenant.toString());
					HikariConfig jdbcConfig = new HikariConfig();
					jdbcConfig.setDriverClassName(tenant.getConfigMap().get("driver"));
					jdbcConfig.setJdbcUrl(tenant.getConfigMap().get("url"));
					jdbcConfig.setUsername(tenant.getConfigMap().get("username"));
					jdbcConfig.setPassword(tenant.getConfigMap().get("password"));
					// jdbcConfig.setConnectionTestQuery(baseDataSourceConfig.getValidationQuery());
					// jdbcConfig.setConnectionInitSql(baseDataSourceConfig.getValidationQuery());
					// 设置其他数据源配置属性
					jdbcConfig.setConnectionTimeout(baseDataSourceConfig.getConnectionTimeout());
					jdbcConfig.setMaxLifetime(baseDataSourceConfig.getMaxLifetime());
					jdbcConfig.setIdleTimeout(baseDataSourceConfig.getIdleTimeout());
					jdbcConfig.setMaximumPoolSize(baseDataSourceConfig.getMaximumPoolSize());
					jdbcConfig.setMinimumIdle(baseDataSourceConfig.getMinimumIdle());
					jdbcConfig.setLeakDetectionThreshold(baseDataSourceConfig.getLeakDetectionThreshold());
					try {
						HikariDataSource dataSource = new HikariDataSource(jdbcConfig);
						dataSourceMap.put(tenant.getTenantId(), dataSource);
						// 同时保存租户信息map
						tenantMap.put(tenant.getTenantId(), tenant);
					} catch (Exception e) {
						// 如果在创建某个租户数据源的过程中,某个租户的数据源挂了(比如:DBA手抖把某个租户数据库用户名密码或者数据库名不小心改了,为了不影响其他租户的启动,此处单独try..catch)
						log.error("初始化【" + tenant.getTenantId() + "】租户数据源失败，可能原因是该租户数据库不存在或者主库中该租户的数据源配置有误");
						log.error("", e);
					}
				}
			}
			// 把租户信息map备份到全局变量中,可用于切换时判断数据源是否有效,也方便其他类获取租户数据源信息(此步非必要)
			TenantContext.tenantInfoMap.putAll(tenantMap);
			// }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 设置数据源,并执行afterPropertiesSet,备注:必须执行afterPropertiesSet此操作，才会重新初始化AbstractRoutingDataSource
		 * 中的 resolvedDataSources， 只有这样，Spring管理的数据源总库中才真正set了这些数据源
		 */
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		dynamicDataSource.afterPropertiesSet();
	}

	/**
	 * 获取基础数据库中租户信息
	 *
	 * @param conn          数据库连接对象
	 * @param queryTenantId 需要查询的tenantId(可选参数,查询指定租户时可以用到)
	 * @return
	 */
	public List<Tenant> getTenant(Connection conn, String queryTenantId) {
		// 1.准备返回容器
		List<Tenant> resultList = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 2.编写sql语句
			StringBuilder sb = new StringBuilder(
					"SELECT t.id,t.tenant_id,t.tenant_name,c.config_key,c.config_value FROM tenant t left join tenant_config c on t.tenant_id = c.tenant_id");
			if (StringUtils.isNotBlank(queryTenantId)) {
				sb.append(" where t.tenant_id = ?");
			}
			sb.append(" order by t.tenant_id");
			// 3.获取sql预编译对象
			ps = conn.prepareStatement(sb.toString());
			// 4.设置参数给占位符
			if (StringUtils.isNotBlank(queryTenantId)) {
				ps.setString(1, queryTenantId);
			}
			// 4.执行并保存结果集
			rs = ps.executeQuery();
			// 5.处理结果集
			Map<String, Tenant> resultMap = new HashMap<>(16);
			while (rs.next()) {
				String tenantId = rs.getString("tenant_id");
				if (null == resultMap.get(tenantId)) {
					Tenant tenant = Tenant.builder().build();
					tenant.setId(rs.getString("id"));
					tenant.setTenantId(rs.getString("tenant_id"));
					tenant.setTenantName(rs.getString("tenant_name"));
					Map<String, String> configMap = new LinkedHashMap<>(16);
					configMap.put(rs.getString("config_key"), rs.getString("config_value"));
					tenant.setConfigMap(configMap);
					resultMap.put(tenant.getTenantId(), tenant);
				} else {
					Tenant tenant = resultMap.get(tenantId);
					Map<String, String> configMap = tenant.getConfigMap();
					configMap.put(rs.getString("config_key"), rs.getString("config_value"));
				}
			}
			// 封装成List返回
			resultList = new ArrayList<>();
			for (Tenant tenant : resultMap.values()) {
				resultList.add(tenant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放连接资源
			release(conn, ps, rs);
		}
		return resultList;
	}

	/**
	 * 验证表是否存在:返回true表示存在,false表示不存在
	 *
	 * @param conn
	 * @param tableName
	 * @return 返回true表示存在, false表示不存在
	 */
	private boolean validateTableNameExist(Connection conn, String tableName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = true;
		// 2.编写sql语句
		String sql = "select count(1) from " + tableName;
		try {
			// 3.获取sql预编译对象
			ps = conn.prepareStatement(sql);
			// 4.执行并保存结果集
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// 如果抛出了table不存在异常,说明数据源中tenant表不存在,说明当前是【单机版】
			result = false;
		} finally {
			// 释放连接资源
			release(conn, ps, rs);
		}
		return result;
	}

	/**
	 * 释放连接资源
	 *
	 * @param conn Connection
	 * @param ps   PreparedStatement
	 * @param rs   ResultSet
	 */
	private static void release(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
