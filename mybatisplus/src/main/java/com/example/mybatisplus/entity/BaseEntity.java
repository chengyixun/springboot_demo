package com.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** @Author: wangyu @Date: Created 2020-12-14 16:14 @Description: @Modified By: */
@Data
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /** 自动填充 fill */
  /** 创建者 */
  @TableField(value = "create_user", fill = FieldFill.INSERT)
  private String createUser;

  /** 创建时间 */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private Date createTime;

  /** 更新者 */
  @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
  private String updateUser;

  /** 更新时间 */
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  /** 删除状态 0 未删除 1已删除 */
  protected Integer deleted;
}
