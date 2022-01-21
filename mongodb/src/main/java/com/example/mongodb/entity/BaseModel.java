package com.example.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * {@link BaseModel}
 *
 * @author Liyaohui
 * @date 5/31/21
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // 用来去除数据中的空值
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String CREATE_TIME = "create_time";
	/** 主键ID */
	protected Long id;

	/** 创建日期 */
	protected Date createTime;

	/** 更新日期 */
	protected Date updateTime;

	/** 删除状态 0 未删除 1已删除 */
	protected Integer deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/** handler before data insert into database */
	public void preInsert() {
		if (null == this.updateTime) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			this.updateTime = calendar.getTime();
		}
		if (null == this.createTime) {
			this.createTime = this.updateTime;
		}
		if (null == this.deleted) {
			this.deleted = 0; // 默认未删除
		}
	}

	/** handler before data update into database */
	public void preUpdate() {
		if (null == this.updateTime) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			this.updateTime = calendar.getTime();
		}
	}
}
