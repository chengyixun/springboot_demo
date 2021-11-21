package com.example.jpamultitenancy.controller;

import com.example.jpamultitenancy.common.util.JsonUtils;
import com.example.jpamultitenancy.tenant.service.LoginService;
import com.example.jpamultitenancy.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName: LoginController @Author: amy @Description: LoginController @Date: 2021/6/22 @Version:
 * 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {

  @Autowired private LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody @Valid LoginVO loginVO) {
    return new ResponseEntity<>(JsonUtils.success(loginService.login(loginVO)), HttpStatus.OK);
  }
}
