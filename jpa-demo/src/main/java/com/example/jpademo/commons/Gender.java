package com.example.jpademo.commons;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** @ClassName: Gender @Author: amy @Description: Gender @Date: 2022/3/10 @Version: 1.0 */
@Getter
@RequiredArgsConstructor
public enum Gender {

  /** 未知 */
  @JsonEnumDefaultValue
  unknown(-1),
  /** 女 */
  female(0),
  /** 男 */
  male(1);

  private final Integer code;

  /**
   * Of gender.
   *
   * @param code the code
   * @return the gender
   */
  @NotNull
  public static Gender of(@NotNull Integer code) {
    for (Gender value : values()) {
      if (value.getCode().equals(code)) {
        return value;
      }
    }
    return unknown;
  }
}
