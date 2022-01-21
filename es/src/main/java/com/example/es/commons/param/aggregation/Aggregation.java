package com.example.es.commons.param.aggregation;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 10:27
 * @Description:
 * @Modified By:
 */
@Data
public class Aggregation {

	/**
	 * 要聚合的字段
	 */
	private String field;

	/**
	 * 聚合类型 eg：sum 、count、avg
	 */
	private AggregationEnum type;

	/**
	 * 聚合后赋值到该字段
	 */
	private String as;

	Aggregation(String field, AggregationEnum type, String as) {
		this.field = field;
		this.type = type;
		this.as = as;
	}

	public static Aggregation.AggregationBuilder builder() {
		return new Aggregation.AggregationBuilder();
	}

	static class AggregationBuilder {

		/**
		 * 要聚合的字段
		 */
		private String field;

		/**
		 * 聚合类型 eg：sum 、count、avg
		 */
		private AggregationEnum type;

		/**
		 * 聚合后赋值到该字段
		 */
		private String as;

		AggregationBuilder() {
		}

		public Aggregation.AggregationBuilder field(String field) {
			this.field = field;
			return this;
		}

		public Aggregation.AggregationBuilder type(AggregationEnum type) {
			this.type = type;
			return this;
		}

		public Aggregation.AggregationBuilder as(String as) {
			this.as = as;
			return this;
		}

		public Aggregation build() {
			return new Aggregation(this.field, this.type, this.as);
		}

		public String toString() {
			return "Aggregation.AggregationBuilder(field=" + this.field + ", type=" + this.type + ", as=" + this.as
					+ ")";
		}
	}
}
