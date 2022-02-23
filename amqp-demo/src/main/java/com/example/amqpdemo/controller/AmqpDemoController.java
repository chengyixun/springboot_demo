package com.example.amqpdemo.controller;

import com.example.amqpdemo.entity.OrderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AmqpDemoController
 * @Author: amy
 * @Description: AmqpDemoController
 * @Date: 2022/2/22
 * @Version: 1.0
 */
@RestController
@RequestMapping("/amqp")
public class AmqpDemoController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/direct")
    public void testSimpleDirect(){
        rabbitTemplate.convertAndSend("girl",  "hello test direct");
    }

    @GetMapping("/direct1")
    public void testJsonDirect(){
        OrderMessage orderMessage = OrderMessage.builder().id("123").name("订单123").build();
        rabbitTemplate.convertAndSend("girl",  orderMessage);
    }


}
