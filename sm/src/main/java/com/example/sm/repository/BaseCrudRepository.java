package com.example.sm.repository;

import com.example.sm.entity.BaseModel;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;

/**
 * {@link BaseCrudRepository} mybatis base crud dao
 *
 * @author Liyaohui
 * @date 7/15/21
 */
public interface BaseCrudRepository<T extends BaseModel>
    extends BaseRepository,
        BaseSelectMapper<T>,
        BaseInsertMapper<T>,
        BaseUpdateMapper<T>,
        BaseDeleteMapper<T>,
        SelectByExampleMapper<T> {}
