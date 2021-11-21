package com.example.tk.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UserTempVO
 * @Author: amy
 * @Description: UserTempVO
 * @Date: 2021/8/16
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTempVO {

	@ExcelProperty(value = "姓名", index = 0)
	private String name;

	@ExcelProperty(value = "年龄", index = 1)
	private Long age;

	@ExcelProperty(value = "性别", index = 2)
	private String sex;
}
