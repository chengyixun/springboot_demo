package com.example.admin.fun;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: Blog
 * @Author: amy
 * @Description: Blog
 * @Date: 2021/10/26
 * @Version: 1.0
 */
@Data
@Builder
public class Blog {

	private String code;

	private String currentCode;
}
