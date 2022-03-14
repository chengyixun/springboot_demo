package com.example.jpademo.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @ClassName: JacksonConfig @Author: amy @Description: JacksonConfig @Date: 2022/3/13 @Version: 1.0
 */
@Configuration
public class JacksonConfig {

  /**
   * 自定义 ObjectMapper的实例化
   *
   * @return
   */
  @Bean
  public ObjectMapper jacksonObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    return objectMapper;
  }
}
