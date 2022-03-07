package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/** @ClassName: OrgUnit @Author: amy @Description: OrgUnit @Date: 2022/3/2 @Version: 1.0 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrganizationUnit {

  @Id private String id;

  private String code;

  private String name;

  private String effectDate;

  private String expireDate;

  private String type;

  private Boolean isValid;

  private String companyId;

  private String locationId;

  private String costCenterId;

  private Integer sortNumber;

  private String versionId;
}
