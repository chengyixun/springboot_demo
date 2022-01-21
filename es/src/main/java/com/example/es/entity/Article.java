package com.example.es.entity;

import io.searchbox.annotations.JestId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-16 15:19
 * @Description:
 * @Modified By:
 */
@Data
@Builder
public class Article {

	@JestId
	private Long id;
	/**
	 * 题目
	 */
	private String title;
	/**
	 * 评论
	 */
	private String content;
	/**
	 * 汇总
	 */
	private String summary;

	/**
	 * 评分
	 */
	private Long pv;
	/**
	 * 作者
	 */
	private String author;
}
