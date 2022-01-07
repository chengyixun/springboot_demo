package com.example.mybatispluscrud.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.example.mybatispluscrud.common.config.CommonServiceConfig;
import com.example.mybatispluscrud.common.config.ValueStyleProperty;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HealthController @Author: amy @Description: HealthController @Date:
 * 2021/12/27 @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class HealthController {

  @Autowired private ValueStyleProperty valueStyleProperty;

  @Autowired private RedisTemplate redisTemplate;

  @Value("${server.port}")
  private String port;

  @Autowired private CommonServiceConfig commonServiceConfig;

  @GetMapping("/change")
  public Map<String, String> getValueChanges() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("port", port);
    map.put("demoKey1", valueStyleProperty.getDemoKey1());
    map.put("algorithmCloudUrl", commonServiceConfig.getAlgorithmCloudUrl());
    return map;
  }

  @GetMapping("/ns/{namespaces}")
  public Map<String, String> getValues(@PathVariable String namespaces) {
    Map<String, String> values = Maps.newHashMap();
    Config config = ConfigService.getConfig(namespaces);
    Set<String> propertyNames = config.getPropertyNames();
    propertyNames.forEach(
        key -> {
          String value = config.getProperty(key, "default value");
          values.put(key, value);
        });

    return values;
  }

  @GetMapping("/redis/{key}")
  public String testRedis(@PathVariable String key) {
    redisTemplate.opsForValue().set(key, "key1_value1", 2, TimeUnit.MINUTES);
    return (String) redisTemplate.opsForValue().get(key);
  }
}
