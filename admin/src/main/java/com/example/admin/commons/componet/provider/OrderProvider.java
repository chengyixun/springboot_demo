package com.example.admin.commons.componet.provider;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;

/** @Author: wangyu @Date: Created 2021-01-06 14:39 @Description: @Modified By: */
@Component
@Slf4j
public class OrderProvider {

  @Autowired private KafkaTemplate kafkaTemplate;

  public void sendMessage(Long orderId, String orderNum, Date date) {
    Order order = Order.builder().orderId(orderId).orderNum(orderNum).createTime(date).build();
    String json = JSONObject.toJSONString(order);
    try {
      ListenableFuture future = kafkaTemplate.send("order_test", json);

      future.addCallback(
          o -> log.info("send-success:{}", json), throwable -> log.info("send-fail:{}", json));
    } catch (Exception e) {
      log.error("send kafka error:{}", e.getMessage());
    }
  }
}
