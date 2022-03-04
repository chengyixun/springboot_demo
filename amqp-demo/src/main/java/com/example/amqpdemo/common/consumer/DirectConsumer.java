package com.example.amqpdemo.common.consumer;

import com.example.amqpdemo.entity.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DirectReceiver @Author: amy @Description: DirectReceiver @Date: 2022/3/4 @Version:
 * 1.0
 */
@Slf4j
@Component
public class DirectConsumer {

  @RabbitListener(queues = "hello-queue")
  public void handler(Message message) {
    log.info("directReceiver:{}", message);
    OrderMessage orderMessage = (OrderMessage) message.getPayload();
    log.info("orderMessage:{}", message);
  }
}
