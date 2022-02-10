package com.example.mongodbcrud;

import com.example.mongodbcrud.entity.Person;
import com.example.mongodbcrud.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: MongodbTest @Author: amy @Description: MongodbTest @Date: 2021/12/10 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = MongodbCrudApplication.class)
@Slf4j
public class MongodbTest {

  @Autowired private MongoTemplate mongoTemplate;

  @Test
  public void testInsert() {
    Person person =
        Person.builder().id(1L).name("里斯").age(30).gender(1).createTime(new Date()).build();
    mongoTemplate.save(person);
  }

  @Test
  public void testQuery() {
    TypedAggregation<Student> aggregation =
        Aggregation.newAggregation(Student.class, Aggregation.group("gender").count().as("sum"));
    log.info("输出聚合对象{}", aggregation.toString());
    AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "stus", Object.class);
    List<Object> mappedResults = results.getMappedResults();
    log.info("输出最后对象{}", mappedResults);
  }
}
