package com.shu.onlineEducation.common.dto.homework;

import lombok.Data;

@Data
public class TaskDto {
	String taskName;
	String content;
	String startTime;
	String endTime;
}
