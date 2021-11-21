package com.example.jpamultitenancy.tenant.entity;

import com.example.jpamultitenancy.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/** @ClassName: Account @Author: amy @Description: Account @Date: 2021/6/28 @Version: 1.0 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "biz_account")
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = Update.class)
  private Long id;

  private String name;

  private Double money;

  @Version private int version;
}
