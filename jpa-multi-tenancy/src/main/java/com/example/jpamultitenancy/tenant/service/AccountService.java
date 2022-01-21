package com.example.jpamultitenancy.tenant.service;

import com.example.jpamultitenancy.tenant.entity.Account;

/**
 * @ClassName: AccountService @Author: amy @Description: AccountService @Date:
 *             2021/6/28 @Version: 1.0
 */
public interface AccountService {

	void create(Account account);

	Account findById(Long id);
}
