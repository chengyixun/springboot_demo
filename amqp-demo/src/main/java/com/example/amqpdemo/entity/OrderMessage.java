package com.example.amqpdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: OrderMessage @Author: amy @Description: OrderMessage @Date: 2022/2/21 @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
  private String id;
  private String name;
}
