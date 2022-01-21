package com.example.jpamultidatasources.dao1;

import com.example.jpamultidatasources.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: BookFirstDao
 * @Author: amy
 * @Description: BookFirstDao
 * @Date: 2021/9/18
 * @Version: 1.0
 */
public interface BookFirstDao extends JpaRepository<Book, Long> {

	List<Book> findAllByUsername(String username);
}
