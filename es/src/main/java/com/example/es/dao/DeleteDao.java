package com.example.es.dao;

import java.io.Serializable;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 17:58
 * @Description:
 * @Modified By:
 */
public interface DeleteDao<E, PK extends Serializable> extends Dao {

	void delete(PK var1);

	void delete(E var1);

	void delete(Iterable<? extends E> var1);
}
