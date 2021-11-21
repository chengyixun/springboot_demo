package com.example.jpamultitenancy.common.interceptor;

import lombok.Data;

/**
 * @ClassName: CurrentLoginUser @Author: amy @Description: CurrentLoginUser @Date:
 * 2021/6/22 @Version: 1.0
 */
@Data
public class CurrentLoginUser {

  private String sessionId;

  private String username;

  private String password;

  private boolean isAdmin;
}
