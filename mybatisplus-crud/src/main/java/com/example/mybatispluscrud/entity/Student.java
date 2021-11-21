package com.example.mybatispluscrud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @ClassName: Student @Author: amy @Description: Student @Date: 2021/11/12 @Version: 1.0 */
@Data
@TableName(value = "biz_student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Long teacherId;

  private String name;
}
