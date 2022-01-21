package com.example.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * {@link Menu}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-09
 */
@Data
public class Menu {

	private String id;

	private String name;

	private Order order;

	private Long time;

	@JSONField
	private List<Menu> children;
}
