package com.example.es.commons.param;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 18:02
 * @Description:
 * @Modified By:
 */
@Data
public class Param implements Cloneable, Serializable {

	/**
	 * 条件
	 */
	private List<Term> terms = new LinkedList();

	/**
	 * 包含字段
	 */
	private Set<String> includes = new LinkedHashSet();

	/**
	 * 剔除字段
	 */
	private Set<String> excludes = new LinkedHashSet();

	private boolean distinct = false;

	public Param() {

	}

	public <T extends Param> T distinct(boolean distinct) {
		this.distinct = distinct;
		return (T) this;
	}

	public <T extends Param> T and(Term term) {
		this.terms.add(term);
		return (T) this;
	}

	public <T extends Param> T or(String column, Object value) {
		return this.or(column, TermEnum.eq, value);
	}

	public <T extends Param> T and(String column, Object value) {
		return this.and(column, TermEnum.eq, value);
	}

	public <T extends Param> T and(String column, TermEnum termType, Object value) {
		Term term = new Term();
		term.setColumn(column);
		term.setTermType(termType);
		term.setValue(value);
		term.setType(Term.Type.and);
		this.terms.add(term);
		return (T) this;
	}

	public <T extends Param> T or(String column, TermEnum termType, Object value) {
		Term term = new Term();
		term.setColumn(column);
		term.setTermType(termType);
		term.setValue(value);
		term.setType(Term.Type.or);
		this.terms.add(term);
		return (T) this;
	}

	public <T extends Param> T includes(String... fields) {
		this.includes.addAll(Arrays.asList(fields));
		this.excludes.removeAll(Arrays.asList(fields));
		return (T) this;
	}

	public <T extends Param> T excludes(String... fields) {
		this.excludes.addAll(Arrays.asList(fields));
		this.includes.removeAll(Arrays.asList(fields));
		return (T) this;
	}

	public <T extends Param> T includes(List<String> fields) {
		this.includes.addAll(fields);
		this.excludes.removeAll(fields);
		return (T) this;
	}

	public <T extends Param> T excludes(List<String> fields) {
		this.excludes.addAll(fields);
		this.includes.removeAll(fields);
		return (T) this;
	}

	public <T extends Param> T where(String column, Object value) {
		this.and(column, value);
		return (T) this;
	}

	public <T extends Param> T where(Term term) {
		this.and(term);
		return (T) this;
	}

	public <T extends Param> T where(String key, TermEnum termType, Object value) {
		this.and(key, termType, value);
		return (T) this;
	}
}
