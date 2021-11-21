package com.example.jpamultitenancy.master.entity;

import com.example.jpamultitenancy.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @ClassName: MasterTenant @Author: amy @Description: MasterTenant @Date: 2021/7/4 @Version: 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sys_master_tenant")
public class MasterTenant extends BaseEntity {

  @Id private String id;

  @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '租户名'")
  private String tenant;

  @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '连接url'")
  private String url;

  @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '用户名'")
  private String username;

  @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '密码'")
  private String password;

  @Version private Integer version;
}
