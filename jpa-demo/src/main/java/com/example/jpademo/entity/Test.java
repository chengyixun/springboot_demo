package com.example.jpademo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/** @ClassName: Test @Author: amy @Description: Test @Date: 2022/3/1 @Version: 1.0 */
@Data
@Entity
@Table(name = "b_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  private Content content;

  @Builder.Default
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime = new Date();
}
