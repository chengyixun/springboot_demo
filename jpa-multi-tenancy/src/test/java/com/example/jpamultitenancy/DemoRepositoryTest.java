package com.example.jpamultitenancy;

import com.example.jpamultitenancy.common.exception.Exceptions;
import com.example.jpamultitenancy.dto.UserBlogDTO;
import com.example.jpamultitenancy.dto.UserRecordDTO;
import com.example.jpamultitenancy.tenant.entity.Blog;
import com.example.jpamultitenancy.tenant.entity.Department;
import com.example.jpamultitenancy.tenant.entity.Group;
import com.example.jpamultitenancy.tenant.entity.User;
import com.example.jpamultitenancy.tenant.repository.BlogRepository;
import com.example.jpamultitenancy.tenant.repository.DepartmentRepository;
import com.example.jpamultitenancy.tenant.repository.GroupRepository;
import com.example.jpamultitenancy.tenant.repository.UserRecordRepository;
import com.example.jpamultitenancy.tenant.repository.UserRepository;
import com.example.jpamultitenancy.tenant.service.GroupService;
import com.example.jpamultitenancy.tenant.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: UserServiceTest @Author: amy @Description: UserServiceTest @Date:
 *             2021/5/25 @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRecordRepository userRecordRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private GroupService groupService;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	public void testDept1() {
		Department department = departmentRepository.findById(1L).orElseThrow(() -> Exceptions.NOT_FOUND());
		System.out.println(department);
	}

	@Test
	public void testInsert() {
		User user = User.builder().username("admin").password("123456").build();
		userRepository.save(user);
	}

	@Test
	public void testUserQuery() {
		List<UserBlogDTO> userBlogs = userRepository.selectUserBlogs();
		userBlogs.forEach(System.out::println);
	}

	@Test
	public void testUserRecordQuery() {
		List<UserRecordDTO> userRecordDTOS = userRecordRepository.aggUserRecords();
		userRecordDTOS.forEach(System.out::println);
	}

	@Test
	public void testBlogsFindBy() {
		List<Blog> blogs = blogRepository.findBlogsByUser_Id(1L);
		blogs.forEach(System.out::println);
	}

	@Test
	public void testUserNativeQuery() {
		User user = userRepository.findByAge(30L);
		System.out.println(user);
	}

	@Test
	public void testUser() {
		List<User> list = userService.list();
		list.forEach(System.out::println);
	}

	@Test
	public void testOne() {
		User one = userService.getOne(1L);
		System.out.println(one);
	}

	@Test
	public void testGroupQuery() {
		Group group = Group.builder().code("ww").name("group_B").build();
		groupRepository.save(group);
		groupRepository.findById(group.getId()).ifPresent(m -> System.out.println(m));
	}

	@Test
	public void testPage() {
		PageRequest pageRequest = PageRequest.of(1, 2);
		Page<Blog> blogs = blogRepository.findAll(pageRequest);
		System.out.println(blogs.getTotalPages());
		System.out.println(blogs.getTotalElements());
		System.out.println(blogs.getContent());
	}

	@Test
	public void test1() {
		groupService.getGroups();
		System.out.println("----------验证缓存是否生效----------");
		Cache cache = cacheManager.getCache("groups");
		System.out.println(cache);
		System.out.println(cache.get(1, String.class));
	}

	@Test
	public void test2() {
		groupService.getGroupById(2L);
		System.out.println("----------验证缓存是否生效----------");
		Cache cache = cacheManager.getCache("group");
		System.out.println(cache);
		System.out.println(cache.get(2, String.class));
	}

}
