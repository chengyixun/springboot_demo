package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.dto.UserBlogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.jpamultitenancy.tenant.entity.User;
import java.util.List;

/**
 * @ClassName: UserRepository @Author: amy @Description: UserRepository @Date: 2021/5/25 @Version:
 * 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

  // SELECT u.username,b.title FROM sys_user u LEFT JOIN biz_blog b ON u.id =
  // b.user_id
  @Query(
      value =
          "SELECT new com.example.jpamultitenancy.dto.UserBlogDTO(u.username,b.title) FROM User u LEFT JOIN Blog b ON u.id = b.user.id ")
  List<UserBlogDTO> selectUserBlogs();

  // nativeQuery= true 以sql语句执行
  // 错误 @Query(value = " SELECT id, username, password FROM sys_user WHERE age =:age", nativeQuery =
  // true)
  @Query(value = " SELECT * FROM sys_user WHERE age =:age", nativeQuery = true)
  User findByAge(@Param("age") Long age);

  // List<User> findAllByCreateTimeBetween();

  User findByUsername(String username);
}
