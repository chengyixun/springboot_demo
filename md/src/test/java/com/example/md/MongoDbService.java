package com.example.md;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: MongoDbService
 * @Author: amy
 * @Description: MongoDbService
 * @Date: 2021/8/6
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoDbService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void testInsert() {
		//Book save = mongoTemplate.save(Book.builder().id("1").name("name").code("code111").build());
		List<Task> tasks = mongoTemplate.find(new Query(), Task.class);

		System.out.println(tasks);
	}

}
