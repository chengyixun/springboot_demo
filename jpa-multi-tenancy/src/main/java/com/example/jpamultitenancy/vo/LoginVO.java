package com.example.jpamultitenancy.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: LoginVO @Author: amy @Description: LoginVO @Date:
 *             2021/6/22 @Version: 1.0
 */
@Data
public class LoginVO {

	@NotBlank(message = "用户名不能为空")
	private String username;

	@NotBlank(message = "密码不能为空")
	private String password;

	@NotBlank(message = "租户不能为空")
	private String tenant;
}
