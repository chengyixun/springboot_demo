package com.example.tk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName: User @Author: amy @Description: User @Date: 2021/7/23 @Version:
 *             1.0
 */
@Data
@Table(name = "biz_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String sex;

	private Long age;

	private Date createTime;

	private Date updateTime;
}
