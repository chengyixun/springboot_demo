package com.example.es.dao.dynamic;

import com.example.es.dao.Dao;
import org.springframework.web.servlet.tags.Param;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 18:01
 * @Description:
 * @Modified By:
 */
public interface DeleteByParamDao extends Dao {

    <Q extends Param> int delete(Q var1);
}
