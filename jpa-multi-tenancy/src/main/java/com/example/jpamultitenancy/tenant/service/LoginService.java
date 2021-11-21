package com.example.jpamultitenancy.tenant.service;

import com.example.jpamultitenancy.dto.LoginUser;
import com.example.jpamultitenancy.vo.LoginVO;
import org.springframework.lang.NonNull;

/**
 * @ClassName: LoginService @Author: amy @Description: LoginService @Date: 2021/6/22 @Version: 1.0
 */
public interface LoginService {

  LoginUser login(@NonNull LoginVO loginVO);
}
