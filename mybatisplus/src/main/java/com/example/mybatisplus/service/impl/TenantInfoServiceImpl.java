package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.entity.TenantInfo;
import com.example.mybatisplus.mapper.TenantInfoMapper;
import com.example.mybatisplus.service.TenantInfoService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TenantInfoServiceImpl @Author: amy @Description:
 *             TenantInfoServiceImpl @Date: 2021/10/11 @Version: 1.0
 */
@Service
public class TenantInfoServiceImpl extends ServiceImpl<TenantInfoMapper, TenantInfo> implements TenantInfoService {
}
