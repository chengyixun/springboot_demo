package com.example.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Author: wangyu @Date: Created 2020-12-08 14:51 @Description: @Modified By: */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AdminApplication.class)
@Slf4j
public class RedisTest {

  private static final String REDIS_KEY_LIST = "mylist";

  private static final String REDIS_KEY_LIST_IDS = "ids-metadata";

  @Autowired private RedisTemplate redisTemplate;

  @Test
  public void testPop() {
    redisTemplate.opsForValue().set("key333", "3444444");

    String value = (String) redisTemplate.opsForValue().get("key333");
    System.out.println(value);
  }

  @Test
  public void testDel() {
    Boolean key333 = redisTemplate.delete("key333");
    System.out.println(key333);
  }

  @Test
  public void testList() {
    List range = redisTemplate.opsForList().range(REDIS_KEY_LIST, 0, -1);
    assert CollectionUtils.isNotEmpty(range);
    range.forEach(System.out::println);
  }

  @Test
  public void testRedisListPush() {
    String value =
        "{\n"
            + "  \"timestamp\": \"2020-12-30 15:09:01\",\n"
            + "  \"flow_id\": 317375418196591,\n"
            + "  \"in_iface\": \"enp3s0\",\n"
            + "  \"event_type\": \"fileinfo\",\n"
            + "  \"vlan\": [\n"
            + "    4072\n"
            + "  ],\n"
            + "  \"src_ip\": \"10.10.20.113\",\n"
            + "  \"src_port\": 58974,\n"
            + "  \"dest_ip\": \"117.139.137.23\",\n"
            + "  \"dest_port\": 61125,\n"
            + "  \"proto\": \"TCP\",\n"
            + "  \"tx_id\": 0,\n"
            + "  \"http_hostname\": \"117.139.137.23\",\n"
            + "  \"http_url\": \"/src/login.php\",\n"
            + "  \"http_http_user_agent\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)\",\n"
            + "  \"http_accept\": \"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, image/png, */*\",\n"
            + "  \"http_accept_charset\": \"iso-8859-1,*,utf-8\",\n"
            + "  \"http_http_method\": \"GET\",\n"
            + "  \"http_protocol\": \"HTTP/1.1\",\n"
            + "  \"http_length\": 0,\n"
            + "  \"host\": \"localhost.localdomain\",\n"
            + "  \"alert\":{\n"
            + "    \"signature_id\":10103\n"
            + "  }\n"
            + "}";

    Long aLong = redisTemplate.opsForList().leftPush(REDIS_KEY_LIST_IDS, value);
    System.out.println(aLong);
  }

  @Test
  public void multi() {
    // redisTemplate.opsForList().leftPush()
    BoundListOperations boundListOperations = redisTemplate.boundListOps(REDIS_KEY_LIST);
    Long size = boundListOperations.size();
    System.out.println(size);
    redisTemplate.executePipelined(
        new SessionCallback<Object>() {
          @Override
          public <K, V> Object execute(RedisOperations<K, V> operations)
              throws DataAccessException {
            return null;
          }
        });
  }

  @Test
  public void testPipelineSet() {

    // 1.executePipelined 重写 入参 RedisCallback 的doInRedis方法
    List pipelined =
        redisTemplate.executePipelined(
            new RedisCallback<Object>() {
              @Override
              public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 2.connection 打开管道
                connection.openPipeline();
                // 3.connection 给本次管道内添加 要一次性执行的多条命令
                // 3.1 一个set操作
                connection.set("mykey1".getBytes(), "StringValue".getBytes());
                // 3.2 批量set操作
                Map<byte[], byte[]> tuple = new HashMap<>();
                tuple.put("m_mykey1".getBytes(), "m_value1".getBytes());
                tuple.put("m_mykey2".getBytes(), "m_value2".getBytes());
                tuple.put("m_mykey3".getBytes(), "m_value3".getBytes());
                connection.mSet(tuple);
                // 3.3 一个get操作
                connection.get("mykey1".getBytes());
                // 注意 4.关闭管道 不需要close 否则拿不到返回值 connection.closePipeline();

                // 这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
              }
            },
            redisTemplate.getStringSerializer());

    for (Object str : pipelined) {
      System.out.println(str);
    }
  }

  @Test
  public void pipelineGet() {
    RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
    List<String> list =
        redisTemplate.executePipelined(
            new RedisCallback<Object>() {
              @Override
              public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 1; i < 4; i++) {
                  String key = "m_mykey" + i;

                  connection.get(key.getBytes());
                }
                return null;
              }
            },
            redisTemplate.getStringSerializer());
    for (String str : list) {
      System.out.println(str);
    }
  }

  @Test
  public void testGet() {
    String value = (String) redisTemplate.opsForValue().get("mykey1");
    System.out.println(value);
  }
}
