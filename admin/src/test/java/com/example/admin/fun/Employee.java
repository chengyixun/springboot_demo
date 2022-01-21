package com.example.admin.fun;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: Employee @Author: amy @Description: Employee @Date:
 *             2021/10/26 @Version: 1.0
 */
@Data
@Builder
public class Employee {
	private String name;
	private String code;
}
