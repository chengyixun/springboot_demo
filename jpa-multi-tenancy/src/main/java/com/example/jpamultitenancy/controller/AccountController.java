package com.example.jpamultitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpamultitenancy.tenant.entity.Account;
import com.example.jpamultitenancy.tenant.service.AccountService;

/**
 * @ClassName: AccountController @Author: amy @Description: AccountController @Date:
 * 2021/6/28 @Version: 1.0
 */
@RestController()
@RequestMapping("/api/account")
public class AccountController {
  @Autowired private AccountService accountService;

  @PostMapping
  public void create(@RequestBody Account account) {
    accountService.create(account);
  }

  @GetMapping
  public ResponseEntity<Account> query(@RequestParam Long accountId) {
    return new ResponseEntity<>(accountService.findById(accountId), HttpStatus.OK);
  }
}
