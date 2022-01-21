package com.example.es.commons.param.aggregation;

import com.example.es.commons.exception.NotSupportedException;
import com.example.es.commons.param.Param;
import com.google.common.collect.Lists;
import lombok.Data;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-12 22:25
 * @Description:
 * @Modified By:
 */
@Data
public class AggregationParam extends Param {

	/**
	 *
	 */
	private List<AggregationBuilder> groupBys = Lists.newArrayList();

	/**
	 * 聚合的条件
	 */
	private List<AggregationBuilder> aggregations = Lists.newArrayList();

	public static AggregationParam empty() {
		return new AggregationParam();
	}

	/**
	 * todo 待验证
	 *
	 * @param field
	 * @param value
	 * @return
	 */
	public static AggregationParam single(String field, Object value) {
		return empty().where(field, value);
	}

	public AggregationParam groupBy(String... groupBys) {
		Stream var10000 = Arrays.stream(groupBys).map((field) -> {
			return AggregationBuilders.terms(field).field(field).size(2147483647);
		});
		List var10001 = this.groupBys;
		var10000.forEach(var10001::add);
		return this;
	}

	/**
	 * @param groupBy 按照该字段聚合
	 * @param as      作为
	 * @return
	 */
	public AggregationParam groupByAs(String groupBy, String as) {
		this.groupBys.add(AggregationBuilders.terms(as).field(groupBy).size(2147483647));
		return this;
	}

	public AggregationParam groupByAs(Integer precision, String groupBy) {
		return this.groupByAs(precision, groupBy, groupBy);
	}

	/**
	 * @param precision 精确度
	 * @param groupBy
	 * @param as
	 * @return
	 */
	public AggregationParam groupByAs(Integer precision, String groupBy, String as) {
		this.groupBys.add(AggregationBuilders.geohashGrid(as).precision(precision).field(groupBy).size(2147483647));
		return this;
	}

	public AggregationParam groupBy(AggregationBuilder... groupBys) {
		this.groupBys.addAll(Arrays.asList(groupBys));
		return this;
	}

	public AggregationParam aggregation(Aggregation... aggregations) {
		Stream var10000 = Arrays.stream(aggregations).map((configField) -> {
			String field = configField.getField();
			String as = configField.getAs();
			switch (configField.getType()) {
			case AVG:
				return AggregationBuilders.avg(as).field(field);
			case MAX:
				return AggregationBuilders.max(as).field(field);
			case MIN:
				return AggregationBuilders.min(as).field(field);
			case SUM:
				return AggregationBuilders.sum(as).field(field);
			case COUNT:
				return AggregationBuilders.count(as).field(field);
			case STATS:
				return AggregationBuilders.stats(as).field(field);
			default:
				throw new NotSupportedException();
			}
		});
		List var10001 = this.aggregations;
		var10000.forEach(var10001::add);
		return this;
	}

	public AggregationParam aggregationParam(AggregationBuilder... aggregationBuilders) {
		this.aggregations.addAll(Arrays.asList(aggregationBuilders));
		return this;
	}

}
