package com.example.mybatisplus;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * @ClassName: RedisConfigurationTest @Author: amy @Description:
 *             RedisConfigurationTest @Date: 2021/9/23 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class)
@Slf4j
public class RedisConfigurationTest {

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() {
		redisTemplate.opsForValue().set("author_test", "xxxyyy");

		String result = (String) redisTemplate.opsForValue().get("author_test");
		System.out.println(result);

		LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate
				.getConnectionFactory();
		Set<RedisNode> clusterNodes = lettuceConnectionFactory.getClusterConfiguration().getClusterNodes();
		log.info("redis nodes:{}", clusterNodes);
	}

}
