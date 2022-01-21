package com.example.sm.entity;

import javax.persistence.Table;

/**
 * {@link ShopTabModel}
 *
 * @author Liyaohui
 * @date 7/15/21
 */
@Table(name = "biz_shop_tab")
public class ShopTabModel extends BaseModel {

	/** 店铺ID */
	private Long shopId;

	/** 店铺导航标签页ID */
	private Long tabId;

}
