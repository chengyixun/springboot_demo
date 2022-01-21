package com.example.mybatispluscrud.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author amy
 */
@Data
@Component
@ConfigurationProperties(prefix = "mpc.common")
public class CommonServiceConfig {
	/** 算法云服务 */
	private String algorithmCloudUrl;
}
