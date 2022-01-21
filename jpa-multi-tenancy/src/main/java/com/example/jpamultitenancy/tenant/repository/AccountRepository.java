package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.tenant.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: AccountRepository @Author: amy @Description:
 *             AccountRepository @Date: 2021/6/28 @Version: 1.0
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
