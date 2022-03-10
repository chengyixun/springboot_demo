package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** @ClassName: Dept @Author: amy @Description: Dept @Date: 2022/3/10 @Version: 1.0 */
@Data
@Builder
@Entity
@Table(name = "t_dept")
@NoArgsConstructor
@AllArgsConstructor
public class Dept  {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  /** 部门名称 */
  private String name;

  /** 员工信息 */
 // @OneToMany private List<Employee> employees;
}
