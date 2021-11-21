package com.example.admin.fun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: GroupChild2
 * @Author: amy
 * @Description: GroupChild2
 * @Date: 2021/8/27
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondChild {

	private String name;

	private String code;

	private List<String> ids;
}
