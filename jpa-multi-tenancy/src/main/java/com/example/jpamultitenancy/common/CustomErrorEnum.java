package com.example.jpamultitenancy.common;

import lombok.Getter;

/**
 * {@link CustomErrorEnum}
 *
 * @author Liyaohui
 * @date 6/20/21
 */
@Getter
public enum CustomErrorEnum {
  UNAUTHORIZED_CODE_ERR(4001, "未登录"),
  PERMISSION_NOT_ENOUGH(4005, "权限不足，请联系管理员"),
  ;

  private final Integer code;

  private final String message;

  CustomErrorEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
