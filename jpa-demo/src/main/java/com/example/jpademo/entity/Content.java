package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @ClassName: Content @Author: amy @Description: Content @Date: 2022/3/1 @Version: 1.0 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content {

  private String name;

  private String host;

  private String remark;
}
