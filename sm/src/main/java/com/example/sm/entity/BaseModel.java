package com.example.sm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * {@link BaseModel}
 *
 * @author Liyaohui
 * @date 7/15/21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CREATE_TIME = "create_time";
	/** 主键ID */
	@Id
	@KeySql(useGeneratedKeys = true)
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

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

	public void preUpdate() {
		if (null == this.updateTime) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			this.updateTime = calendar.getTime();
		}
	}
}
