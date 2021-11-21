package com.example.jpamultitenancy.tenant.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/** @ClassName: Department @Author: amy @Description: Department @Date: 2021/5/25 @Version: 1.0 */
@Data
@Entity
@Table(name = "sys_dept")
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private String name;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "sys_dept_user",
      joinColumns = @JoinColumn(name = "dept_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;

  private Date createTime;
  private Date updateTime;
}
