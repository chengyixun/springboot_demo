package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: OrganizationHierarchy @Author: amy @Description: OrganizationHierarchy @Date:
 * 2022/3/7 @Version: 1.0 组织架构上下级关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrganizationHierarchy {

  @Id private String id;

  private String path;

  private String unitId;

  private String parentId;

  private String versionId;

}
