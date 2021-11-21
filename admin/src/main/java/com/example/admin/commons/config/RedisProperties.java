package com.example.admin.commons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author taowenchao
 * @Date 2019/5/31 16:29
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private int database;
    private String host;
    private int port;
    private String password;
}
