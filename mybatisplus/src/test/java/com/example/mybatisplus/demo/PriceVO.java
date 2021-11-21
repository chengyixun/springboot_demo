package com.example.mybatisplus.demo;

import lombok.Data;

/**
 * {@link PriceVO}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-22
 */
@Data
public class PriceVO {
  private Integer firstWeight;
  private String firstPrice;
  private AddPriceVO addPrice;
}
