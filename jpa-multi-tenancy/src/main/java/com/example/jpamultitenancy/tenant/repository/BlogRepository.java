package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.tenant.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: BlogRepository @Author: amy @Description: BlogRepository @Date:
 *             2021/5/26 @Version: 1.0
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

	List<Blog> findBlogsByUser_Id(Long userId);
}
