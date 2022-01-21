package com.example.jpamultitenancy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @ClassName: CacheProperties @Author: amy @Description: @Date:
 *             2021/6/21 @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "gac.cache")
public class CacheProperties {

	private Boolean enabled = true;

	private Long expireTime;
}
