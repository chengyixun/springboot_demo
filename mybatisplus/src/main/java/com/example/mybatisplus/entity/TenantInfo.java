package com.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: TenantInfo @Author: amy @Description: TenantInfo @Date:
 *             2021/10/11 @Version: 1.0
 */
@Data
@Builder
@TableName(value = "sys_tenant_info")
public class TenantInfo {

	private String id;

	private String tenant;

	private String url;

	private String username;

	private String password;
}
