package com.example.sm.repository;

import com.example.sm.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link OrderRepository}
 *
 * @author Liyaohui
 * @date 7/19/21
 */
@Mapper
public interface OrderRepository extends BaseCrudRepository<Order> {

   // Page<Order> queryOrderPage(@Param("request")OrderPageVO pageVO);
}
