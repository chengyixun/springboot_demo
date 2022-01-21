package com.example.jpamultidatasources;

import com.example.jpamultidatasources.dao1.BookFirstDao;
import com.example.jpamultidatasources.dao2.BookSecondDao;
import com.example.jpamultidatasources.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: JpaTest @Author: amy @Description: JpaTest @Date:
 *             2021/9/18 @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {
	@Autowired
	private BookFirstDao bookFirstDao;

	@Autowired
	private BookSecondDao bookSecondDao;

	@Test
	public void testQuery() {
		List<Book> firstBooks = bookFirstDao.findAll();
		System.out.println(firstBooks);

		List<Book> secondBooks = bookSecondDao.findAll();
		System.out.println(secondBooks);

	}

	@Test
	public void testQuery2() {
		List<Book> all = bookSecondDao.findAll();

	}
}
