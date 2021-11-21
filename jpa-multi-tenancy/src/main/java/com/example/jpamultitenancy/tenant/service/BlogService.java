package com.example.jpamultitenancy.tenant.service;

import java.util.List;

import com.example.jpamultitenancy.tenant.entity.Blog;

/** @ClassName: BlogService @Author: amy @Description: BlogService @Date: 2021/6/16 @Version: 1.0 */
public interface BlogService {

  void create(Blog blog);

  List<Blog> list();
}
