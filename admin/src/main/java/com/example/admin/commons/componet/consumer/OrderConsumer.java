package com.example.admin.commons.componet.consumer;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/** @Author: wangyu @Date: Created 2021-01-06 14:36 @Description: @Modified By: */
@Component
@Slf4j
public class OrderConsumer {

  @KafkaListener(topics = "order_test")
  public void consumer(String json) {
    log.info("currentTime:{}, consumer message:{}", DateUtil.date(), json);
  }
}
