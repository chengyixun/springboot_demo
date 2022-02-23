package com.example.amqpdemo.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: HelloWorldConfig
 * @Author: amy
 * @Description: HelloWorldConfig
 * @Date: 2022/2/22
 * @Version: 1.0
 */
@Configuration
public class HelloWorldConfig {

    public static final String HELLO_WORLD_QUEUE_NAME = "hello_world_queue";

    @Bean
    Queue queue1() {
        return new Queue(HELLO_WORLD_QUEUE_NAME);
    }
}
