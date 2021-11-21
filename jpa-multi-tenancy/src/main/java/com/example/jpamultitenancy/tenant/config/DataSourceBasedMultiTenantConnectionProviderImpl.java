package com.example.jpamultitenancy.tenant.config;

import com.example.jpamultitenancy.common.util.DataSourceUtils;
import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.example.jpamultitenancy.master.repository.MasterTenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: DataSourceBasedMultiTenantConnectionProviderImpl @Author: amy @Description:
 * 提供租户标识对应的租户数据源信息
 *
 * <p>通过查询租户数据源库，动态获得租户数据源信息，为租户业务模块的数据源配置提供数据数据支持。 @Date: 2021/7/4 @Version: 1.0
 */
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl
    extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  @Autowired private MasterTenantRepository masterTenantRepository;

  private Map<String, DataSource> dataSources = new TreeMap<>();

  @Override
  protected DataSource selectAnyDataSource() {
    if (dataSources.isEmpty()) {
      loadDataSources();
    }
    return dataSources.values().iterator().next();
  }

  @Override
  protected DataSource selectDataSource(String tenantIdentifier) {
    if (!dataSources.containsKey(tenantIdentifier)) {
      loadDataSources();
    }
    return dataSources.get(tenantIdentifier);
  }

  private void loadDataSources() {
    List<MasterTenant> tenants = masterTenantRepository.findAll();
    tenants.forEach(
        tenant -> dataSources.put(tenant.getTenant(), DataSourceUtils.wrapperDataSource(tenant)));
  }
}
