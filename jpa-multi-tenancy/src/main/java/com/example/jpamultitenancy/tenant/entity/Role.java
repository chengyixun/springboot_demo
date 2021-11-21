package com.example.jpamultitenancy.tenant.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName: Role @Author: amy @Description: 角色 简单考虑下 人员/角色/部门 以及登陆这块 写个demo @Date:
 * 2021/5/26 @Version: 1.0
 */
@Data
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long id;

  private String name;

  private String description;

  /*@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
  private Set<User> users; */

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "sys_roles_menus",
      joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "menu_id")})
  private Set<Menu> menus;
}
