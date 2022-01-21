package com.example.jpamultidatasources.dao2;

import com.example.jpamultidatasources.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: BookSecondDao
 * @Author: amy
 * @Description: BookSecondDao
 * @Date: 2021/9/18
 * @Version: 1.0
 */
public interface BookSecondDao extends JpaRepository<Book, Long> {

	List<Book> findAllByUsername(String username);

}
