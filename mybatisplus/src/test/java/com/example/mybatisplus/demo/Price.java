package com.example.mybatisplus.demo;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

/**
 * {@link Price} 价格
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-19
 */
@Builder
@Data
public class Price {
  private Integer firstWeight;
  private String firstPrice;
  private AddPrice addPrice;

  public static void main(String[] args) {
    AddPrice addPrice =
        AddPrice.builder().begin(1).end(2).createTime(System.currentTimeMillis()).build();

    Price price = Price.builder().addPrice(addPrice).firstPrice("333").firstWeight(34).build();

    PriceVO priceVO = new PriceVO();
    // BeanUtils.copyProperties(price, priceVO);
    // System.out.println(priceVO);

    // 使用 gson 的序列化与反序列化
    Gson gson = new Gson();
    String json = gson.toJson(price);
    PriceVO vo = gson.fromJson(json, PriceVO.class);
    System.out.println(vo);
  }
}
