package com.example.jpacrud;

import com.example.jpacrud.entity.User;
import com.example.jpacrud.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: JpaRepositoryTest
 * @Author: amy
 * @Description:
 * @Date: 2021/7/8
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test1() {
		User user = userRepository.findByUsername("test1");
		System.out.println(user.getRoles());

	}
}
