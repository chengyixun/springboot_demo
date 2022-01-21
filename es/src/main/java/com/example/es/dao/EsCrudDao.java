package com.example.es.dao;

import com.example.es.commons.param.CrudEntity;
import com.example.es.commons.param.aggregation.AggregationParam;
import io.searchbox.core.search.aggregation.Aggregation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 17:51
 * @Description:
 * @Modified By:
 */
@NoRepositoryBean
public interface EsCrudDao<E extends CrudEntity<PK>, PK extends Serializable> extends CrudDao<E, PK> {

	<T> List<T> select(AggregationParam var1, Class<T> var2);
}
