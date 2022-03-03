package com.example.jpademo;

import com.example.jpademo.entity.Content;
import com.example.jpademo.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class JpaDemoApplicationTests {

  @Autowired private TestRepository testRepository;

  @Test
  void contextLoads() {
    List<com.example.jpademo.entity.Test> results = testRepository.findAll();
    log.info("result:{}", results);
  }


  @Test
  void testInsert(){
    Content content = Content.builder().name("amy").host("www.amy.com").build();
    com.example.jpademo.entity.Test  test = new com.example.jpademo.entity.Test();
    test.setContent(content);
    testRepository.save(test);
  }

  @Test
  void testSelectByParam(){

  }
}
