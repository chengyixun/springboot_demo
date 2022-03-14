package com.example.jpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/** @ClassName: Menu @Author: amy @Description: Menu @Date: 2022/3/4 @Version: 1.0 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
  /** id */
  private Integer id;
  /** 名称 */
  private String name;
  /** 父id，根节点为0 */
  private Integer parentId;
  /** 子节点信息 */
  private List<Menu> childList;

  private Date createDate;

  public Menu(Integer id, String name, Integer parentId) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
  }
}
