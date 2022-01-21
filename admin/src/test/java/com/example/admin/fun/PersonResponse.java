package com.example.admin.fun;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: PersonResponse
 * @Author: amy
 * @Description: PersonResponse
 * @Date: 2021/8/22
 * @Version: 1.0
 */
@Data
public class PersonResponse {

	private String personId;

	private List<Support> supportList;
}
