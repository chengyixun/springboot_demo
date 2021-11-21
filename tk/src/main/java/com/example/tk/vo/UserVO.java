package com.example.tk.vo;

import com.example.tk.common.annotation.ExcelColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UserVO
 * @Author: amy
 * @Description: UserVO
 * @Date: 2021/8/15
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	@ExcelColumn(value = "姓名")
	private String name;

	@ExcelColumn(value = "年龄")
	private Long age;

	@ExcelColumn(value = "性别")
	private String sex;
}
