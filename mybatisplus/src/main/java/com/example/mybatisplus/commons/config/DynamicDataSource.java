package com.example.mybatisplus.commons.config;

import com.example.mybatisplus.commons.context.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @ClassName: DynamicDataSource @Author: amy @Description: DynamicDataSource 动态数据源实现类 @Date:
 * 2021/10/11 @Version: 1.0
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

  /**
   * 如果希望所有数据源在启动配置时就加载好，这里通过设置数据源Key值来切换数据，定制这个方法
   * 可以根据上下文 获取当前租户标识
   * @return
   */
  @Override
  protected Object determineCurrentLookupKey() {
    return DynamicDataSourceContextHolder.getDataSourceKey();
  }

  /**
   * 根据租户标识 获取对应的dataSource
   * @return
   */
  @Override
  protected DataSource determineTargetDataSource() {
    return super.determineTargetDataSource();
  }

  /**
   * 设置默认数据源
   * @param defaultDataSource
   */
  public void setDefaultDataSource(Object defaultDataSource) {
    super.setDefaultTargetDataSource(defaultDataSource);
  }

  /**
   * 设置数据源
   * @param dataSources
   */
  public void setDataSources(Map<Object, Object> dataSources) {
    super.setTargetDataSources(dataSources);
    // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
    DynamicDataSourceContextHolder.addDataSourceKeys(dataSources.keySet());
    log.info(">>> DynamicDataSource.setDataSources");
  }
}
