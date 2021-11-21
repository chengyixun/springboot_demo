package com.example.es.commons.param.aggregation;

import lombok.Getter;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 10:40
 * @Description:
 * @Modified By:
 */
@Getter
public enum AggregationEnum {
    SUM,
    COUNT,
    AVG,
    MAX,
    MIN,
    STATS;

    private AggregationEnum() {

    }
}
