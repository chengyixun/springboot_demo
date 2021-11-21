package com.example.es.dao;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 17:53
 * @Description:
 * @Modified By:
 */
public interface InsertDao<E, PK> extends Dao {

    PK insert(E var1);

    <S extends E> List<PK> insert(Iterable<S> var1);
}
