package com.example.sc.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Author: amy
 * @Description: BaseEntity
 * @Date: 2021/6/16
 * @Version: 1.0
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //开启审计监听
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(updatable = false)
	private String createBy;

	@CreatedDate
	@Column(updatable = false)
	private Date createTime;

	@LastModifiedBy
	private String updateBy;

	@LastModifiedDate
	private Date updateTime;

}
