package com.example.admin.fun.entity;

import lombok.Builder;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @ClassName: LaborDemandVO @Author: amy @Description: LaborDemandVO @Date: 2022/1/24 @Version: 1.0
 */
@Data
@Builder
public class LaborDemandVO {
  private String workshopId;
  private Long productionLineId;
  private Long workSectionId;
  private Long postId;
  private List<ShiftNumVO> details = Lists.newArrayList();

  public static LaborDemandVO of(
      String workshopId,
      Long productionLineId,
      Long workSectionId,
      Long postId,
      List<ShiftNumVO> details) {
    return LaborDemandVO.builder()
        .workshopId(workshopId)
        .productionLineId(productionLineId)
        .workSectionId(workSectionId)
        .postId(postId)
        .details(details)
        .build();
  }
}
