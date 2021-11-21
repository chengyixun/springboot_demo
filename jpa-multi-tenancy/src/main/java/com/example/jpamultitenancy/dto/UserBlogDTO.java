package com.example.jpamultitenancy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @ClassName: UserDTO @Author: amy @Description: UserDTO @Date: 2021/5/25 @Version: 1.0 */
@Data
@AllArgsConstructor
public class UserBlogDTO {

  private String username;
  // private String password;
  private String title;
}
