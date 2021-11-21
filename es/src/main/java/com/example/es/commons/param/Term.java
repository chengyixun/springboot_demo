package com.example.es.commons.param;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 18:04
 * @Description: 条件对象
 * @Modified By:
 */
@Data
public class Term implements Cloneable, Serializable {

    /**
     * 字段
     */
    private String column;

    /**
     * 字段值
     */
    private Object value;

    /**
     * 第一个条件间的类型
     */
    private Term.Type type;

    /**
     * cloumn和value之间的 关系类型
     */
    private TermEnum termType;

    /**
     * 子条件
     */
    private List<Term> terms;

    public Term() {
        this.type = Type.and;
        this.termType = TermEnum.eq;
        this.terms = new LinkedList<>();
    }


    public Term or(Term andTerm) {
        andTerm.setType(Type.or);
        this.terms.add(andTerm);
        return this;
    }

    public Term or(String column, Object value) {
        return this.and(column, TermEnum.eq, value);
    }

    public Term or(String column, TermEnum termType, Object value) {
        Term term = new Term();
        term.column = column;
        term.termType = termType;
        term.value = value;
        term.type = Type.or;
        this.terms.add(term);
        return this;
    }


    public Term and(Term andTerm) {
        andTerm.setType(Type.and);
        this.terms.add(andTerm);
        return this;
    }

    public Term and(String column, Object value) {
        return this.and(column, TermEnum.eq, value);
    }

    /**
     * 添加子条件and的方式
     *
     * @param column
     * @param termType
     * @param value
     * @return
     */
    public Term and(String column, TermEnum termType, Object value) {
        Term term = new Term();
        term.column = column;
        term.termType = termType;
        term.value = value;
        term.type = Type.and;
        this.terms.add(term);
        return this;
    }


    public static Term build(String column, Object value) {
        return build(column, TermEnum.eq, value);
    }

    public static Term build(String column, TermEnum operator, Object value) {
        Term term = new Term();
        term.column = column;
        term.value = value;
        term.termType = operator;
        return term;
    }


    public static enum Type {
        or, and;

        private Type() {

        }

        public static Term.Type fromString(String str) {
            try {
                return valueOf(str.toLowerCase());
            } catch (Exception var2) {
                return and;
            }

        }
    }
}
