package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.tenant.entity.Blog;
import com.example.jpamultitenancy.tenant.repository.BlogRepository;
import com.example.jpamultitenancy.tenant.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: BlogServiceImpl @Author: amy @Description: BlogServiceImpl @Date:
 *             2021/6/16 @Version: 1.0
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	@Override
	public void create(Blog blog) {
		blogRepository.save(blog);
	}

	@Override
	public List<Blog> list() {
		return blogRepository.findAll();
	}
}
