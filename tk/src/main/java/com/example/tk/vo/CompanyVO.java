package com.example.tk.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: CompanyVO
 * @Author: amy
 * @Description: CompanyVO
 * @Date: 2021/7/28
 * @Version: 1.0
 */
@Data
public class CompanyVO {

    private String id;

    private String name;

    private List<Integer> results;
}
