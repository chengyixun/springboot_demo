package com.example.jpamultitenancy.controller;

import com.example.jpamultitenancy.tenant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController @Author: amy @Description: UserController @Date: 2021/7/4 @Version:
 * 1.0
 */
@RestController()
@RequestMapping("/biz/user")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping()
  @PreAuthorize(value = "@el.check('user:list')")
  public ResponseEntity<Object> getAll() {
    return new ResponseEntity<>(userService.list(), HttpStatus.OK);
  }
}
