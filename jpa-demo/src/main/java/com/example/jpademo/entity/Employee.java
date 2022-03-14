package com.example.jpademo.entity;

import com.example.jpademo.commons.Gender;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;

/** @ClassName: Employee @Author: amy @Description: Employee @Date: 2022/3/10 @Version: 1.0 */
@Data
@Entity
@Table(name = "t_employee")
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  /** 员工姓名 */
  private String name;
  /** 员工手机号 */
  private String phone;
  /** 身份证号码 */
  private String idCard;
  /** 员工性别 */
  private Gender gender;
  /** 入职日期 */
  private LocalDate entryDate;
  /** 离职日期 */
  private LocalDate turnoverDate;
  /** 所在部门 */
  @ManyToOne private Dept dept;

  @JsonUnwrapped @Transient private Address address;
}
