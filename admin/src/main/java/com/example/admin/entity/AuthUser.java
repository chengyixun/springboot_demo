package com.example.admin.entity;

import com.example.admin.commons.annotation.WriteNullListAsNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-29 16:47
 * @Description:
 * @Modified By:
 */
@Data
@WriteNullListAsNull
public class AuthUser {

	private String id;

	private Order order;

	private Long time;

	private String name;

	private Integer age;

	private LocalDateTime createTime = LocalDateTime.now();

	private List<String> users;
}
