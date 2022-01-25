package com.example.admin.fun.entity;

import lombok.Builder;
import lombok.Data;

/** @ClassName: ShiftNumVO @Author: amy @Description: ShiftNumVO @Date: 2022/1/24 @Version: 1.0 */
@Data
@Builder
public class ShiftNumVO {
  private Long dayShiftNum;
  private Long nightShiftNum;
  private String productionDate;

  public static ShiftNumVO of(String productionDate, Long num) {
    return ShiftNumVO.builder()
        .productionDate(productionDate)
        .nightShiftNum(num)
        .dayShiftNum(num + 1)
        .build();
  }
}
