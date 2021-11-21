package com.example.es.commons.param.page;

import com.example.es.commons.param.Entity;
import com.example.es.commons.param.QueryParam;
import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 17:46
 * @Description:
 * @Modified By:
 */
@Data
public class PageResult<E> implements Entity {

    /**
     * 数据总条数
     */
    private Long total;

    /**
     * 数据总页数
     */
    private Long totalPages;

    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 当前页条数
     */
    private int pageSize;
    /**
     * 查询结果
     */
    private E data;

    // TODO: 2021-01-12 此处后面再加

    public void setPage(QueryParam queryParam) {
        this.pageNo = queryParam.getPageNo();
        this.pageSize = queryParam.getPageSize();
        if (this.pageSize > 0) {
            this.totalPages = this.total % (long) this.pageSize == 0 ? this.total / (long) this.pageSize : this.total / (long) this.pageSize + 1L;
        }
    }

    public void setTotal(long total) {
        this.total = total;
        if (this.pageSize > 0) {
            this.totalPages = this.total % (long) this.pageSize == 0L ? this.total / (long) this.pageSize : this.total / (long) this.pageSize + 1L;
        }

    }
}
