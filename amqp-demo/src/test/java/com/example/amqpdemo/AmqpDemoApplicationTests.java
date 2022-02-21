package com.example.amqpdemo;

import com.alibaba.fastjson.JSONObject;
import com.example.amqpdemo.entity.OrderMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpDemoApplicationTests {

  @Autowired private RabbitTemplate rabbitTemplate;

  @Test
  void testSend() {
    OrderMessage orderMessage = OrderMessage.builder().id("123").name("订单123").build();
    String message = JSONObject.toJSONString(orderMessage);
    rabbitTemplate.convertAndSend("exchange.direct", "girl", message);
  }
}
