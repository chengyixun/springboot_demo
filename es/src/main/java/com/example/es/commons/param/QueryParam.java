package com.example.es.commons.param;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:21
 * @Description: 添加分页、排序
 * @Modified By:
 */
@Data
public class QueryParam extends Param {

    /**
     * 是否分页开关
     * 默认分页
     */
    private boolean paging = true;

    /**
     * 当前页大小
     * 默认分页大小为25条
     */
    private int pageSize = 25;

    /**
     * 当前页码
     * 默认从第零页开始
     */
    private int pageNo = 0;

    /**
     * 总数
     */
    private Long total;

    /**
     * 排序
     */
    private List<Sort> sorts = new LinkedList<>();


    public Sort orderBy(String column) {
        Sort sort = new Sort(column, this);
        this.sorts.add(sort);
        return sort;
    }

    // TODO: 2021-01-12 待完善

}
