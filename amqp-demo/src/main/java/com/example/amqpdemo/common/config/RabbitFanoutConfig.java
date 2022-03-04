package com.example.amqpdemo.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitFanoutConfig @Author: amy @Description: RabbitFanoutConfig @Date:
 * 2022/3/4 @Version: 1.0 在这种策略中，routingkey 将不起任何作用
 */
@Configuration
public class RabbitFanoutConfig {

  /** fanout模式的交换机名称 */
  public static final String FANOUTNAME = "test-fanout";

  @Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange(FANOUTNAME, true, false);
  }

  @Bean
  Queue queueOne() {
    return new Queue("queue-one");
  }

  @Bean
  Queue queueTwo() {
    return new Queue("queue-two");
  }

  @Bean
  Binding bindingOne() {
    return BindingBuilder.bind(queueOne()).to(fanoutExchange());
  }

  @Bean
  Binding bindingTwo() {
    return BindingBuilder.bind(queueTwo()).to(fanoutExchange());
  }
}
