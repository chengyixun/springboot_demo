package com.example.mongodb;

import com.example.mongodb.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @ClassName: MongodbTest @Author: amy @Description: MongodbTest @Date: 2021/12/10 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = MongodbApplication.class)
@Slf4j
public class MongodbTest {

  @Autowired private MongoTemplate mongoTemplate;

  @Test
  public void testInsert() {
    Person person =
        Person.builder().id(1L).name("张三").age(30).gender(1).createTime(new Date()).build();
    mongoTemplate.save(person);
  }
}
