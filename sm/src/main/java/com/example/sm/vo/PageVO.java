package com.example.sm.vo;

/**
 * {@link PageVO}
 *
 * @author Liyaohui
 * @date 7/19/21
 */

public class PageVO {

	private Integer pageNum;
	private Integer pageSize;
	private String orderBy;

	public PageVO(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

}
