package com.example.admin.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: User
 * @Author: amy
 * @Description: User
 * @Date: 2021/10/26
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class User {

	private String code;

	/** 年龄 */
	private int age;
}
