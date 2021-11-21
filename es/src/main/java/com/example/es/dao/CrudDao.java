package com.example.es.dao;

import com.example.es.commons.param.CrudEntity;
import com.example.es.dao.dynamic.DeleteByParamDao;
import com.example.es.dao.dynamic.QueryByParamDao;
import com.example.es.dao.dynamic.UpdateByParamDao;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 22:13
 * @Description:
 * @Modified By:
 */
@NoRepositoryBean
public interface CrudDao<E extends CrudEntity<PK>, PK extends Serializable> extends InsertDao<E,PK> ,UpdateDao<E,PK>, DeleteDao<E, PK>, QueryDao<E, PK>, DeleteByParamDao, UpdateByParamDao<E>, QueryByParamDao<E>, Repository<E, PK> {
}
