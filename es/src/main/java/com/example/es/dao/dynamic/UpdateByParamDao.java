package com.example.es.dao.dynamic;

import com.example.es.commons.param.UpdateParam;
import com.example.es.dao.Dao;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:41
 * @Description:
 * @Modified By:
 */
public interface UpdateByParamDao<E> extends Dao {

    int update(UpdateParam<E> var1);

    int patch(UpdateParam<E> var1);
}
