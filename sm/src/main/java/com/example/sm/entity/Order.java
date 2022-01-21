package com.example.sm.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @ClassName: Order @Author: amy @Description: Order @Date: 2021/7/14 @Version:
 *             1.0
 */
@Data
@Table(name = "biz_order")
public class Order extends BaseModel {

	private String code;

	private String name;

	private String remark;

}
