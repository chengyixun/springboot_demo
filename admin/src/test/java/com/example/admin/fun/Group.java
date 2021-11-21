package com.example.admin.fun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: Group
 * @Author: amy
 * @Description: Group
 * @Date: 2021/8/27
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

	private String name;

	private String code;

	private List<FirstChild> firsts;
}
