package com.example.mybatispluscrud.common.listener;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ApolloConfigListener @Author: amy @Description: ApolloConfigListener
 * apollo配置监听 @Date: 2021/12/27 @Version: 1.0
 */
@Slf4j
@Configuration
public class ApolloConfigListener implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Autowired private RefreshScope refreshScope;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

//  @ApolloConfigChangeListener
  private void onChangeConfig(ConfigChangeEvent changeEvent) {
    log.info(">> apollo-config-change start……");
    for (String key : changeEvent.changedKeys()) {
      ConfigChange change = changeEvent.getChange(key);
      log.info(
          "key:{},propertyName:{},oldValue:{},newValue:{}",
          key,
          change.getPropertyName(),
          change.getOldValue(),
          change.getNewValue());
    //  this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
      // refreshScope.refreshAll();
    }
    log.info(">> apollo-config-change end……");
  }
}
