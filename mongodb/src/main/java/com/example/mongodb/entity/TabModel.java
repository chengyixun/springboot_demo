package com.example.mongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * {@link TabModel}
 *
 * @author Liyaohui
 * @date 5/31/21
 */
@Data
@Document(collection = "biz_tab")
public class TabModel extends BaseModel {

  /** 语言 */
  private Integer langType;

  /** tab 名称 */
  private String name;

  /** tab路由 */
  private String router;

  /** 路由参数 */
  private Map<String, Object> param;

  /** 排序 从小到大 */
  private Integer sort;

  /** 排序 开关 */
  private Boolean enableSort;

  /** 类型 0：pc，1: 小程序 */
  private Integer type;

  /** true:展示，false：隐藏 */
  private Boolean show;

  /** 展示/隐藏 开关 ，true：可以操作，false：不可以操作 */
  private Boolean enableShow;

  /** 装修控制 开关 */
  private Boolean flag;

  /** 描述 */
  private String description;

  /** tab对应的路径 */
  private String path;

  /** 页签标识 */
  private String key;
}
