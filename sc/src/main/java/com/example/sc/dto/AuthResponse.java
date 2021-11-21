package com.example.sc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Md. Amran Hossain
 */
@Data
@AllArgsConstructor
public class AuthResponse {

	private String userName;
	private String token;
}
