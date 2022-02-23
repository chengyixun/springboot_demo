package com.example.amqpdemo.common.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @ClassName: RabbitMQConfig @Author: amy @Description: RabbitMQConfig @Date: 2022/2/21 @Version:
 * 1.0
 */
//@Configuration
public class RabbitMQConfig {



//  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    // 设置序列化策略
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

//  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /*@Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(factory);
    //解决: Caused by: org.springframework.amqp.AmqpException: No method found for class [B 异常
    //消息转换器,消息转化为json, setMessageConverter(MessageConverter ),这个方法就是这次异常的解决方案,创建一个Jackson2JsonMessageConverter对象放进去
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    return factory;
  }*/

}
