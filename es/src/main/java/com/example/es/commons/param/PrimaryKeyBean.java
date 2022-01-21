package com.example.es.commons.param;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 15:42
 * @Description:
 * @Modified By:
 */
public interface PrimaryKeyBean<PK> {

	String ID = "id";

	PK getId();

	void setId(PK var1);
}
