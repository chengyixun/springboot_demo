package com.example.mybatispluscrud;

import com.example.mybatispluscrud.entity.Employee;
import com.example.mybatispluscrud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: ContiperfTest @Author: amy @Description: ContiperfTest @Date:
 *             2021/11/2 @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisplusCrudApplication.class)
@Slf4j
public class ContiperfTest {

	@Autowired
	private EmployeeService employeeService;

	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	/** 模拟高并发下 接口的修改异常 */
	@Test
	@PerfTest(invocations = 2, threads = 2)
	public void test1() {
		Employee employee = employeeService.getById(1L);
		AtomicInteger atomicInteger = new AtomicInteger();
		atomicInteger.getAndIncrement();
		log.info("ThreadName:{},第：{} 次, data:{}", Thread.currentThread().getName(), atomicInteger.get(),
				employee.getName());
	}
}
