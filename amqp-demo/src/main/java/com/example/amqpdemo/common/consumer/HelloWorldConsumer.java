package com.example.amqpdemo.common.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.amqpdemo.common.config.HelloWorldConfig.HELLO_WORLD_QUEUE_NAME;

/**
 * @ClassName: HelloWorldConsumer @Author: amy @Description: HelloWorldConsumer @Date:
 * 2022/2/22 @Version: 1.0
 */
@Slf4j
@Component
public class HelloWorldConsumer {

  /*@RabbitListener(queues = HELLO_WORLD_QUEUE_NAME)
  public void consumer(String message) {
    log.info(">> consumer1 message:{},ack:auto",message);
  }

  @RabbitListener(queues = HELLO_WORLD_QUEUE_NAME,concurrency = "10")
  public void receive2(String msg) {
    log.info(">> consumer2 message:{},T:{},ack:auto", msg,Thread.currentThread().getName());
  }*/

  /**
   * 测试 手动提交api
   *
   * @param message
   * @param channel
   * @throws IOException
   */
  @RabbitListener(queues = HELLO_WORLD_QUEUE_NAME)
  public void receive(Message message, Channel channel) throws IOException {
    log.info(">>consumer1 message:{},ack:manual", message.getPayload());
    channel.basicAck(((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG)), true);
  }

  // 此时第二个消费者拒绝了所有消息，第一个消费者消费了所有消息
  @RabbitListener(queues = HELLO_WORLD_QUEUE_NAME, concurrency = "11")
  public void receive2(Message message, Channel channel) throws IOException {
    log.info(
        ">>consumer2 message:{}.T:{},ack:manual",
        message.getPayload(),
        Thread.currentThread().getName());
    channel.basicReject(((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG)), true);
  }
}
