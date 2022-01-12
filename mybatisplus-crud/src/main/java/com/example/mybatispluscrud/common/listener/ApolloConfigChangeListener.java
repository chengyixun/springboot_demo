package com.example.mybatispluscrud.common.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: ApolloConfigChangeListener @Author: amy @Description:
 * ApolloConfigChangeListener @Date: 2021/12/28 @Version: 1.0
 */
@Slf4j
@Component
public class ApolloConfigChangeListener implements ApplicationContextAware {

  @Value("${apollo.bootstrap.namespaces:application}")
  private String namespace;

  private ApplicationContext applicationContext;

  @Resource private RefreshScope refreshScope;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * 监听对应的namespace的配置变动，refresh
   *
   * <p>1. placeholder 能自动刷新
   *
   * <p>2. @ConfigurationProperties() 需要监听并刷新
   *
   * <p>3. todo mysql和redis 的暂时不去监听变化 实现动态关闭连接池，修改配置后需要重启，但是生产这2项配置一般不会随意变动
   */
  @Bean
  public void changeListener() {
    Config config = ConfigService.getConfig(namespace);
    config.addChangeListener(
        new ConfigChangeListener() {
          @Override
          public void onChange(ConfigChangeEvent changeEvent) {
            System.out.println("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
              ConfigChange change = changeEvent.getChange(key);
              System.out.println(
                  String.format(
                      "Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                      change.getPropertyName(),
                      change.getOldValue(),
                      change.getNewValue(),
                      change.getChangeType()));
              // 更新@ConfigurationProperties注解的bean的属性值，还会更新日志系统的相关配置，例如日志级别等
              // EnvironmentChangeEvent会被两个监听者监听，LoggingRebinder和ConfigurationPropertiesRebinder
              applicationContext.publishEvent(
                  new EnvironmentChangeEvent(changeEvent.changedKeys()));
              refreshScope.refreshAll();
            }
          }
        });
  }
}
