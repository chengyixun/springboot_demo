package com.example.amqpdemo.common.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: ConsumerMessageListener @Author: amy @Description: ConsumerMessageListener @Date:
 * 2022/2/21 @Version: 1.0
 */
@Slf4j
@Component
public class ConsumerMessageListener {

  /**
   * 监听指定队列 @RabbitListener 指定了 exchange 、key、Queue 后，如果 Rabbitmq 没有会去创建
   *
   * @param message 消息体
   * @param headers 消息头
   * @param channel 通道
   * @return
   */
  @RabbitListener(
      bindings =
          @QueueBinding(
              exchange = @Exchange("exchange.direct"),
              key = "girl",
              value = @Queue("girl")))
  public void listenerMessage(String message, @Headers Map<String, Object> headers, Channel channel)
      throws IOException {
    log.info("message:{} headers:{}", message, headers);
    // 手动 ack
    channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
  }
}
