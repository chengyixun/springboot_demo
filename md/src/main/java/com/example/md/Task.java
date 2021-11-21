package com.example.md;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: Task
 * @Author: amy
 * @Description: Task
 * @Date: 2021/8/6
 * @Version: 1.0
 */
@Data
@Document(collation = "dsTaskData")
public class Task {

	@Id
	private String id;

	private String taskId;

}
