package com.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName: Employee @Author: amy @Description: Employee @Date:
 *             2021/8/27 @Version: 1.0
 */
@Data
@TableName(value = "biz_employee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	private Long id;

	private String name;

	private Integer age;

	private String email;

	private Long managerId;

	private LocalDateTime createTime;
}
