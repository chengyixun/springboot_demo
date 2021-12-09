package com.example.mybatispluscrud;

import com.example.mybatispluscrud.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/** @ClassName: RedisTest @Author: amy @Description: RedisTest @Date: 2021/12/8 @Version: 1.0 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusCrudApplication.class)
@Slf4j
public class RedisTest {

  @Autowired private RedisTemplate redisTemplate;

  @Test
  public void test1() {
    redisTemplate.opsForValue().set("key1", "value1111");
    String value = (String) redisTemplate.opsForValue().get("key1");
    log.info("key:{},value:{}", "key1", value);
  }

  @Test
  public void test2() {
    Employee employee = Employee.builder().name("张三").age(10).build();
    redisTemplate.opsForValue().set("key2", employee);
    Employee value = (Employee) redisTemplate.opsForValue().get("key2");
    log.info("key:{},value:{}", "key2", value);
  }

  @Test
  public void test3() {
    List<Employee> employees = Lists.newArrayList();
    Employee e1 = Employee.builder().name("张三").age(10).build();
    Employee e2 = Employee.builder().name("李四").age(20).build();
    employees.add(e1);
    employees.add(e2);

    redisTemplate.opsForValue().set("key3", employees);
    List<Employee> value = (List<Employee>) redisTemplate.opsForValue().get("key3");
    log.info("key:{},value:{}", "key3", value);
  }

  // https://www.jianshu.com/p/0d4aea41a70c
  @Test
  public void test4() {
    Boolean delete = redisTemplate.delete("key3");
    if(delete){
      List<Employee> employees = Lists.newArrayList();
      redisTemplate.opsForValue().set("key3", employees);
      List<Employee> value = (List<Employee>) redisTemplate.opsForValue().get("key3");
      log.info("key:{},value:{}", "key3", value);
    }
  }

  // TODO: 2021/12/9  pipelined 测试 待完善
  @Test
  public void testPipelined() {
    List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
      @Override
      public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
        redisConnection.openPipeline();
        for (int i = 0; i < 10000; i++) {
          String key = "key_" + i;
        //  redisConnection.
          redisConnection.zCount(key.getBytes(), 0,Integer.MAX_VALUE);
        }
        return null;
      }
    }, redisTemplate.getValueSerializer());

  }
}
