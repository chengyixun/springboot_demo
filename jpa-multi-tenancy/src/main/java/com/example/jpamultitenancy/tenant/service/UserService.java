package com.example.jpamultitenancy.tenant.service;

import com.example.jpamultitenancy.tenant.entity.User;

import java.util.List;

/** @ClassName: UserService @Author: amy @Description: UserService @Date: 2021/5/25 @Version: 1.0 */
public interface UserService {

  List<User> list();

  User getOne(Long id);

  void save(User user);

  User findById(Long id);

  User findByUsername(String username);

  void saveAll(List<User> users);
}
