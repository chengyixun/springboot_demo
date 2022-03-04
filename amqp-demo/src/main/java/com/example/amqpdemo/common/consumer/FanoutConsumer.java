package com.example.amqpdemo.common.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: FanoutConsumer @Author: amy @Description: FanoutConsumer @Date: 2022/3/4 @Version:
 * 1.0
 */
@Slf4j
@Component
public class FanoutConsumer {

  @RabbitListener(queues = "queue-one")
  public void handle1(String message) {
    log.info(">> FanoutConsumer handle1:{}", message);
  }

  @RabbitListener(queues = "queue-two")
  public void handle2(String message) {
    log.info(">> FanoutConsumer handle2:{}", message);
  }
}
