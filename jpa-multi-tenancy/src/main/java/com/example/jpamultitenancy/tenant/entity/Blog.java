package com.example.jpamultitenancy.tenant.entity;

import com.example.jpamultitenancy.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @ClassName: Blog @Author: amy @Description: Blog @Date: 2021/5/25 @Version: 1.0 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "biz_blog")
public class Blog extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = Update.class)
  private Long id;

  @NotBlank(message = "标题不能为空")
  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;
}
