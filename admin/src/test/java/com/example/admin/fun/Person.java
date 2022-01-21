package com.example.admin.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@link Person}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-25
 */
@Data
@AllArgsConstructor
public class Person {
	/** 姓名 */
	private String name;
	/** 薪资 */
	private int salary;
	/** 年龄 */
	private int age;
	/** 性别 */
	private String sex;
	/** 地区 */
	private String area;

	private String createTime;

	private String flag;
	// 1,2,3,4

	public Person(String name) {
		this.name = name;
	}
}
