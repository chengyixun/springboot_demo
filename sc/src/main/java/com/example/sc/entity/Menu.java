package com.example.sc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @ClassName: Menu
 * @Author: amy
 * @Description: Menu
 * @Date: 2021/7/6
 * @Version: 1.0
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_menu")
@ToString
public class Menu extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 父级id
	 */
	private Long pid;

	@ManyToMany(mappedBy = "menus", cascade = CascadeType.ALL)
	private Set<Role> roles;

	/**
	 * 菜单类型
	 */
	private String type;

	/**
	 * 菜单标题
	 */
	private String title;

	/**
	 * 组件名称
	 */
	private String name;

	/**
	 * 组件
	 */
	private String component;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 权限
	 */
	private String permission;

}
