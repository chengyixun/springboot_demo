package com.example.es.dao;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 18:00
 * @Description:
 * @Modified By:
 */
public interface QueryDao<E, PK> extends Dao {

    E findOne(PK var1);

}
