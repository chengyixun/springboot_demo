package com.example.mybatismultitenant.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: TenantConfig @Author: amy @Description: TenantConfig @Date:
 *             2021/10/9 @Version: 1.0
 */
@Data
@Builder
@TableName(value = "tenant_config")
public class TenantConfig {

	private String id;

	/** 租户标识ID（唯一） */
	private String tenantId;

	/** 租户数据源具体配置-键 */
	private String configKey;

	/** 租户数据源具体配置-值 */
	private String configValue;
}
