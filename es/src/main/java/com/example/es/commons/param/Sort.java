package com.example.es.commons.param;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:25
 * @Description:
 * @Modified By:
 */
@Data
public class Sort {

	private String name;

	private SortEnum order;

	private QueryParam param;

	public Sort() {
		this.order = SortEnum.DESC;
	}

	public Sort(String name) {
		this.name = name;
		this.order = SortEnum.DESC;
	}

	public Sort(String column, QueryParam queryParam) {
		this.order = SortEnum.DESC;
		this.name = column;
		this.param = queryParam;
	}

	public QueryParam asc() {
		this.order = SortEnum.ASC;
		return this.param;
	}

	public QueryParam desc() {
		this.order = SortEnum.DESC;
		return this.param;
	}

}
