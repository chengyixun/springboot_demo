package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @ClassName: Address @Author: amy @Description: Address @Date: 2022/3/11 @Version: 1.0 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
  private String doorNumber;
  private String street;
  private String pinCode;
  private String city;
}
