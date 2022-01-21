package com.example.sc;

import com.example.sc.entity.User;
import com.example.sc.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: JpaTest
 * @Author: amy
 * @Description: JpaTest
 * @Date: 2021/7/7
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test1() {

		User user = userRepository.findByUsername("test1");
		System.out.println(user);
	}
}
