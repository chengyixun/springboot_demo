package com.example.mybatismultitenant.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName: Tenant @Author: amy @Description: Tenant 租户实体 用于存储租户信息 @Date: 2021/10/9 @Version: 1.0
 */
@Data
@Builder
@TableName(value = "tenant")
public class Tenant {

  private String id;
  private String tenantId;
  private String tenantName;

  private Map<String, String> configMap;
}
