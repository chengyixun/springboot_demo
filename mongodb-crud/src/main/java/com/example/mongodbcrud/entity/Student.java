package com.example.mongodbcrud.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/** @ClassName: Student @Author: amy @Description: Student @Date: 2022/2/9 @Version: 1.0 */
@Data
@Document(collection = "stus")
public class Student {
  private String id;
  private String name;
  private String age;
  private String gender;
  private Double scope;
}
