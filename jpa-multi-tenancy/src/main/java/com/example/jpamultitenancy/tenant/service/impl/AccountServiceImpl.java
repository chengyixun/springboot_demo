package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.common.exception.Exceptions;
import com.example.jpamultitenancy.tenant.entity.Account;
import com.example.jpamultitenancy.tenant.repository.AccountRepository;
import com.example.jpamultitenancy.tenant.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AccountServiceImpl @Author: amy @Description: AccountServiceImpl @Date:
 * 2021/6/28 @Version: 1.0
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired private AccountRepository accountRepository;

  @Override
  public void create(Account account) {
    accountRepository.save(account);
  }

  @Override
  public Account findById(Long id) {
    return accountRepository.findById(id).orElseThrow(() -> Exceptions.NOT_FOUND());
  }
}
