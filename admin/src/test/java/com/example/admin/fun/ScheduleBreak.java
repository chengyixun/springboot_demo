package com.example.admin.fun;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: ScheduleBreak
 * @Author: amy
 * @Description: ScheduleBreak
 * @Date: 2021/11/4
 * @Version: 1.0
 */
@Data
@Builder
public class ScheduleBreak {

	private String unit;

	private String personId;

	private String scheduleDate;

	private Integer status;

}
