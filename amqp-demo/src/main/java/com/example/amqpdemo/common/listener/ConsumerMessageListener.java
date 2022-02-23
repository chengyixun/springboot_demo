package com.example.amqpdemo.common.listener;

import com.example.amqpdemo.entity.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Headers;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: ConsumerMessageListener @Author: amy @Description: ConsumerMessageListener @Date:
 * 2022/2/21 @Version: 1.0
 */
@Slf4j
//@Component
//@RabbitListener(queues = "girl")
public class ConsumerMessageListener {

  /**
   * 监听指定队列 @RabbitListener 指定了 exchange 、key、Queue 后，如果 Rabbitmq 没有会去创建
   *
   * @param message 消息体
   * @param headers 消息头
   * @param channel 通道
   * @return
   */
  /*@RabbitListener(
      bindings =
          @QueueBinding(
              exchange = @Exchange("exchange.direct"),
              key = "girl",
              value = @Queue("girl")))*/
  @RabbitHandler
  public void listenerSimpleMessage(String message, @Headers Map<String, Object> headers)
      throws IOException {
    log.info(">>message:{} headers:{}", message, headers);
    // 手动 ack
   // channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
  }

  @RabbitHandler
  public void listenerJsonMessage(OrderMessage orderMessage, @Headers Map<String, Object> headers)
          throws IOException {
    log.info(">>message:{} headers:{}", orderMessage, headers);
    // 手动 ack
   // channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
  }



}
