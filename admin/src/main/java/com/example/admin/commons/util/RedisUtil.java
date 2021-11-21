package com.example.admin.commons.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-08 16:16
 * @Description:
 * @Modified By:
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;



    
}
