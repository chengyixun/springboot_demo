package com.example.tk;

import lombok.Getter;

/**
 * @ClassName: TabooEnum
 * @Author: amy
 * @Description: TabooEnum
 * @Date: 2021/10/25
 * @Version: 1.0
 */
@Getter
public enum TabooEnum {

	DUST("Dust"), X_RAY("X-ray");

	private String value;

	TabooEnum(String value) {
		this.value = value;
	}

	public static Integer getIndex(String value) {
		for (TabooEnum tabooEnum : values()) {
			if (tabooEnum.getValue().equalsIgnoreCase(value)) {
				return tabooEnum.ordinal();
			}
		}
		return null;
	}

}
