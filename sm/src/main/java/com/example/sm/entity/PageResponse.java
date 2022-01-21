package com.example.sm.entity;

import java.util.List;

/**
 * {@link PageResponse}
 *
 * @author Liyaohui
 * @date 7/19/21
 */
public class PageResponse<T> extends PageInfo {

	/** 列表数据 */
	private List<T> list;

	/** 总条数 */
	private long total;

	/** 总页数 */
	private int pages;

	public PageResponse(com.github.pagehelper.Page<T> page) {
		super(page.getPageNum(), page.getPageSize());
		this.list = page.getResult();
		this.total = page.getTotal();
		this.pages = page.getPages();
	}

	public PageResponse(org.springframework.data.domain.Page<T> page) {
		// Mongo page number start from 0
		super(page.getNumber() + 1, page.getSize());
		this.list = page.getContent();
		this.total = page.getTotalElements();
		this.pages = page.getTotalPages();
	}

	public PageResponse(List<T> list, int pageNum, int pageSize, long total, int pages) {
		super(pageNum, pageSize);
		this.list = list;
		this.total = total;
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
