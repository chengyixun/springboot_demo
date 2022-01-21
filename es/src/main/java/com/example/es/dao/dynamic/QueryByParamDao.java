package com.example.es.dao.dynamic;

import com.example.es.commons.param.Param;
import com.example.es.commons.param.QueryParam;
import com.example.es.dao.Dao;
import com.example.es.commons.param.page.PageResult;

import java.util.Collections;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:43
 * @Description:
 * @Modified By:
 */
public interface QueryByParamDao<E> extends Dao {

	<Q extends Param> List<E> query(Q var1);

	<Q extends Param> Long count(Q var1);

	default PageResult<E> selectPage(QueryParam queryParam) {
		PageResult<E> pageResult = new PageResult<>();
		Long count = this.count(queryParam);
		pageResult.setPage(queryParam);
		pageResult.setTotal(count);
		if (count == 0L) {
			pageResult.setData((E) Collections.EMPTY_LIST);
		} else {
			pageResult.setData((E) this.query(queryParam));
		}

		return pageResult;

	}
}
