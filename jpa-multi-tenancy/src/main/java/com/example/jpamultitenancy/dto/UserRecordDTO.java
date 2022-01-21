package com.example.jpamultitenancy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: UserRecordDTO @Author: amy @Description: UserRecordDTO @Date:
 *             2021/5/25 @Version: 1.0
 */
@Data
@AllArgsConstructor
public class UserRecordDTO {

	private String username;

	private Long offlineNum;

	private Long onlineNum;
}
