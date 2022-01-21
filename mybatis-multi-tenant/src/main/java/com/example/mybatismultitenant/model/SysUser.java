package com.example.mybatismultitenant.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sys_user")
public class SysUser {

	private Long id;

	private String name;

	private String nickName;

	private String avatar;

	private String password;

	private String salt;

	private String email;

	private String mobile;

	private Byte status;

	private Long deptId;

	private String createBy;

	private Date createTime;

	private String lastUpdateBy;

	private Date lastUpdateTime;

	private Byte delFlag;
}
