package com.example.jpamultitenancy.tenant.entity;

import com.example.jpamultitenancy.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** @ClassName: Menu @Author: amy @Description: Menu @Date: 2021/7/6 @Version: 1.0 */
@Entity
@Data
@Table(name = "sys_menu")
public class Menu extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Long id;

  /** 父级id */
  private Long pid;

  /*@ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
  private Set<Role> roles; */

  /** 菜单类型 */
  private String type;

  /** 菜单标题 */
  private String title;

  /** 组件名称 */
  private String name;

  /** 组件 */
  private String component;

  /** 排序 */
  private Integer sort;

  /** 权限 */
  private String permission;
}
