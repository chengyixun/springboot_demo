package com.example.admin;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: User @Author: amy @Description: User @Date: 2021/12/14 @Version:
 *             1.0
 */
@Data
@Builder
public class User {

	private Integer age;

	private String name;
}
