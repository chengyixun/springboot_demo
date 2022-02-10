package com.example.mongodbcrud.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/** @ClassName: Person @Author: amy @Description: Person @Date: 2022/2/9 @Version: 1.0 */
@Data
@Builder
@Document(collection = "biz_person")
public class Person {

  private Long id;

  private String name;

  private Integer age;

  private Integer gender;

  private Date createTime;
}
