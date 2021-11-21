package com.example.mybatisplus.commons.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-15 10:54
 * @Description:
 * @Modified By:
 */
public enum SexEnum implements IEnum<Integer> {
    MAN(1, "男"),
    WOMAN(2, "女");

    private Integer value;

    private String desc;

    SexEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return this.desc;
    }
}
