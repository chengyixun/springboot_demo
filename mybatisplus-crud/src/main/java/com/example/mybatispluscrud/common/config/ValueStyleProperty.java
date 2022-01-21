package com.example.mybatispluscrud.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ValueStyleProperty @Author: amy @Description:
 *             ValueStyleProperty @Date: 2021/12/27 @Version: 1.0
 */
@Data
@Component
public class ValueStyleProperty {

	@Value("${apollo.value.demoKey1}")
	private String demoKey1;
}
