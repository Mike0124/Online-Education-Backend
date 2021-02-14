package com.shu.onlineEducation.common.dto.homework;

import lombok.Data;

@Data
public class HomeworkDto {
	String content;
	Integer studentId;
	Integer taskId;
	Integer status;
}
