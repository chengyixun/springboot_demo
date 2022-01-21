package com.example.tk;

import com.example.tk.entity.User;
import com.example.tk.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: UserServiceTest
 * @Author: amy
 * @Description: UserServiceTest
 * @Date: 2021/8/14
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testUpdate1() {
		User user = new User();
		user.setId(22234);
		user.setName("333");
		int i = userMapper.updateByPrimaryKeySelective(user);
		System.out.println(i);
	}
}
