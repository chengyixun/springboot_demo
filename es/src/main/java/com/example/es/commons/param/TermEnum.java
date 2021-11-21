package com.example.es.commons.param;

import com.sun.xml.internal.xsom.impl.Ref;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 18:07
 * @Description:
 * @Modified By:
 */
@Getter
public enum TermEnum {
    eq("eq"),
    not("not"),
    like("like"),
    nlike("nlike"),
    gt("gt"),
    lt("lt"),
    gte("gte"),
    lte("lte"),
    in("in"),
    nin("nin"),
    empty("empty"),
    nempty("nempty"),
    isnull("isnull"),
    notnull("notnull"),
    btw("btw"),
    nbtw("nbtw"),
    intersects("intersects"),
    disjoint("disjoint"),
    within("within"),
    contains("contains");

    private String value;

    private static final Map<String, TermEnum> CACHE = new LinkedHashMap<>();

    TermEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    static {
        if (values() != null) {
            TermEnum[] values = values();
            int length = values.length;
            for (int i = 0; i < length; i++) {
                TermEnum termEnum = values[i];
                CACHE.put(termEnum.value, termEnum);
            }
        }
    }


    public static TermEnum parse(String operation) {
        return (TermEnum)CACHE.get(operation);
    }
}
