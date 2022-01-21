package com.example.mybatismultitenant.common.config;

import com.example.mybatismultitenant.common.context.TenantContext;
import com.example.mybatismultitenant.model.Tenant;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.example.mybatismultitenant.constant.CommonConstant.BASE_DATASOURCE_KEY;

/**
 * @ClassName: @Author: amy @Description: DynamicLazyDataSource @Date:
 *             2021/10/9 @Version: 1.0
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 【核心】动态数据源维护的所有数据源信息
	 * 备注：Spring维护的所有数据源，实际上放在了AbstractRoutingDataSource抽象类中的targetDataSources属性中
	 * 但是可能是出于安全考虑，AbstractRoutingDataSource只提供了setTargetDataSources的方法，
	 * 却没有提供getTargetDataSources的方法
	 * 这带来了非常大的不便，于是我们在这个子类中单独定义一个私有属性，并额外提供一个getTargetDataSources的方法
	 * 而setTargetDataSources本质上仍然是调用父级的。
	 */
	private Map<Object, Object> targetDataSources;

	public Map<Object, Object> getTargetDataSources() {
		return this.targetDataSources;
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
	}

	@Override
	protected DataSource determineTargetDataSource() {
		log.info("=== determineTargetDataSource ===");
		HikariDataSource dataSource = null;
		super.setLenientFallback(false);
		boolean needReInit = false;
		String tenantId = TenantContext.getTenantId();
		try {
			dataSource = (HikariDataSource) super.determineTargetDataSource();
		} catch (IllegalStateException e) {
			// 捕获了这个异常说明【内存中】没有找到对应tenantId的数据源,则触发防御机制:去[基础数据库]中查一把该租户信息
			needReInit = true;
		}
		log.info("【防御机制】 内存动态数据源中不存在该租户对应的dataSource  >>> 防御开始");
		if (needReInit) {

			DynamicDataSourceInit dynamicDataSourceInit = new DynamicDataSourceInit();

			HikariDataSource baseDataSource = dynamicDataSourceInit.getBaseDataSource();
			if (StringUtils.isNotEmpty(tenantId)) {
				try {
					Connection conn = baseDataSource.getConnection();
					// 1.先去数据库查一下是否有该tenantId对应租户的信息
					List<Tenant> dbTenants = dynamicDataSourceInit.getTenant(conn, tenantId);
					// 2.如果数据库里有信息,说明是最近新添加的租户,
					// 则判断一下是否已启用,如果已启用,就重新初始化一下内存中的动态数据源,然后再调用一次同样的获取方法返回数据;
					if (!CollectionUtils.isEmpty(dbTenants)) {
						// 已启用状态，则初始化数据源
						dynamicDataSourceInit.initDataSource();
						return super.determineTargetDataSource();
					} else {
						// 如果数据库里也没有该租户信息,则该租户确实不存在,抛异常
						throw new RuntimeException("数据库不存在tenantId=" + tenantId + "的租户信息");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("【防御机制】 >>> 防御结束");
		return dataSource;
	}

	/** 如果希望所有数据源在启动配置时就加载好，这里通过设置数据源Key值来切换数据源，定制这个方法 */
	@Override
	protected Object determineCurrentLookupKey() {
		// 如果线程上下文中没有设置tenantId,则传入默认值baseDataSource
		return StringUtils.isNotBlank(TenantContext.getTenantId()) ? TenantContext.getTenantId() : BASE_DATASOURCE_KEY;
	}
}
