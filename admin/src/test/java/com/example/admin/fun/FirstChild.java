package com.example.admin.fun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: GroupChild
 * @Author: amy
 * @Description: GroupChild
 * @Date: 2021/8/27
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FirstChild {

	private String name;

	private List<SecondChild> seconds;
}
