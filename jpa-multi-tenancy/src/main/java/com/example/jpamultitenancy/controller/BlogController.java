package com.example.jpamultitenancy.controller;

import com.example.jpamultitenancy.tenant.entity.Blog;
import com.example.jpamultitenancy.tenant.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: BlogController @Author: amy @Description: BlogController @Date:
 *             2021/6/16 @Version: 1.0
 */
@RestController
@RequestMapping("/biz/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping
	@PreAuthorize("@el.check('blog:list')")
	public ResponseEntity<Object> list() {
		return new ResponseEntity<>(blogService.list(), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("@el.check('blog:add')")
	public ResponseEntity<Object> create(@RequestBody Blog blog) {
		blogService.create(blog);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
