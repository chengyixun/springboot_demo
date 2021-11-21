package com.example.sm.service.impl;

import com.example.sm.entity.Order;
import com.example.sm.repository.OrderRepository;
import com.example.sm.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * {@link OrderServiceImpl}
 *
 * @author Liyaohui
 * @date 7/19/21
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseCrudServiceImpl<OrderRepository, Order>
    implements OrderService {



}
