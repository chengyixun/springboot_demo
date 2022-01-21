package com.example.admin;

import cn.hutool.core.date.DateTime;
import com.example.admin.commons.componet.provider.OrderProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-06 14:59
 * @Description:
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdminApplication.class)
@Slf4j
public class KafkaTest {

	@Autowired
	private OrderProvider orderProvider;

	@Test
	public void testSendMeg() {
		for (int i = 0; i < 5; i++) {
			String uuid = UUID.randomUUID().toString();
			orderProvider.sendMessage(Long.valueOf(i), uuid, new DateTime());
		}

	}
}
