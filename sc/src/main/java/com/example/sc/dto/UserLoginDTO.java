package com.example.sc.dto;

import lombok.Data;

/**
 * @author Md. Amran Hossain
 */
@Data
public class UserLoginDTO {

	private String userName;
	private String password;
	private String tenant;
}
