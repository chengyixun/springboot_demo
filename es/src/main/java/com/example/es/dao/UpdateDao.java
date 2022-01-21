package com.example.es.dao;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 17:56
 * @Description:
 * @Modified By:
 */
public interface UpdateDao<E, PK> extends Dao {

	PK update(E var1);

	<S extends E> List<PK> update(Iterable<S> var1);

	PK upsert(E var1);

	<S extends E> List<PK> upsert(Iterable<S> var1);

	PK patch(E var1);

	<S extends E> List<PK> patch(Iterable<S> var1);

}
