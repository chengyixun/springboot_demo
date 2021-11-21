package com.example.es.dao;

import com.example.es.commons.param.CrudEntity;
import com.example.es.commons.param.UpdateParam;
import com.example.es.commons.param.aggregation.AggregationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.servlet.tags.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 11:55
 * @Description:
 * @Modified By:
 */
@NoRepositoryBean
@Slf4j
public class AbstractEsCrudDao<E extends CrudEntity<PK>,PK extends Serializable> implements EsCrudDao<E,PK> {



    @Override
    public <T> List<T> select(AggregationParam var1, Class<T> var2) {
        return null;
    }

    @Override
    public void delete(PK var1) {

    }

    @Override
    public void delete(E var1) {

    }

    @Override
    public void delete(Iterable<? extends E> var1) {

    }

    @Override
    public PK insert(E var1) {
        return null;
    }

    @Override
    public <S extends E> List<PK> insert(Iterable<S> var1) {
        return null;
    }

    @Override
    public E findOne(PK var1) {
        return null;
    }

    @Override
    public PK update(E var1) {
        return null;
    }

    @Override
    public <S extends E> List<PK> update(Iterable<S> var1) {
        return null;
    }

    @Override
    public PK upsert(E var1) {
        return null;
    }

    @Override
    public <S extends E> List<PK> upsert(Iterable<S> var1) {
        return null;
    }

    @Override
    public PK patch(E var1) {
        return null;
    }

    @Override
    public <S extends E> List<PK> patch(Iterable<S> var1) {
        return null;
    }

    @Override
    public <Q extends Param> int delete(Q var1) {
        return 0;
    }

    @Override
    public <Q extends com.example.es.commons.param.Param> List<E> query(Q var1) {
        return null;
    }

    @Override
    public <Q extends com.example.es.commons.param.Param> Long count(Q var1) {
        return null;
    }

    @Override
    public int update(UpdateParam<E> var1) {
        return 0;
    }

    @Override
    public int patch(UpdateParam<E> var1) {
        return 0;
    }


    // private final Class<E> entityType = ClassUtil.getGenericInterfaces();

  //  protected final Class<PK> primaryKeyType =



}
