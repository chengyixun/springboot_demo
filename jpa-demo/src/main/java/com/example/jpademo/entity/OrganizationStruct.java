package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: OrganizationStruct @Author: amy @Description: OrganizationStruct @Date:
 * 2022/3/7 @Version: 1.0 组织架构版本表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrganizationStruct {

  @Id private String id;

  /** 有效日期 */
  private String effectDate;

  /** 失效日期 */
  private String expireDate;

  private String versionId;



}
