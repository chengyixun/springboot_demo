package com.example.admin.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-06 14:29
 * @Description:
 * @Modified By:
 */
@Data
@Builder
public class Order {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

}
