package com.example.mybatismultitenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatismultitenant.dao.SysUserMapper;
import com.example.mybatismultitenant.model.SysUser;
import com.example.mybatismultitenant.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SysUserServiceImpl @Author: amy @Description: SysUserServiceImpl @Date:
 * 2021/10/12 @Version: 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {}
