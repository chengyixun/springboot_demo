package com.example.es.commons.param;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:40
 * @Description:
 * @Modified By:
 */
@Data
public class UpdateParam<T> extends Param {

	private T data;

	// TODO: 2021-01-12

}
