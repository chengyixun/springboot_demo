package com.example.mybatispluscrud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Teacher
 * @Author: amy
 * @Description: Teacher
 * @Date: 2021/11/12
 * @Version: 1.0
 */
@Data
@TableName(value = "biz_teacher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String name;

	private Integer age;
}
