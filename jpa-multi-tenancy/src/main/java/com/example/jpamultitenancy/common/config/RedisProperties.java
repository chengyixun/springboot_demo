package com.example.jpamultitenancy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/** @Author taowenchao @Date 2019/5/31 16:29 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
	private int database;
	private String host;
	private int port;
	private String password;
}
