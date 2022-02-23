package com.example.amqpdemo;

import com.alibaba.fastjson.JSONObject;
import com.example.amqpdemo.entity.OrderMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.amqpdemo.common.config.HelloWorldConfig.HELLO_WORLD_QUEUE_NAME;

@SpringBootTest
class AmqpDemoApplicationTests {

  @Autowired private RabbitTemplate rabbitTemplate;

  @Test
  void testSend() {
    OrderMessage orderMessage = OrderMessage.builder().id("123").name("订单123").build();
    String message = JSONObject.toJSONString(orderMessage);
    rabbitTemplate.convertAndSend("girl", message);
  }

  /** simple demo ack：auto */
  @Test
  public void testHelloSend() {
    rabbitTemplate.convertAndSend(HELLO_WORLD_QUEUE_NAME, "hello world test");
  }

  /** Work queues ack：auto */
  @Test
  public void testConcurrencyConsumer() {
    for (int i = 0; i < 10; i++) {
      rabbitTemplate.convertAndSend(HELLO_WORLD_QUEUE_NAME, "hello");
    }
  }



}
