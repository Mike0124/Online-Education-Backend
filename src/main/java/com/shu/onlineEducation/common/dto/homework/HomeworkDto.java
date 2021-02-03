package com.shu.onlineEducation.common.dto.homework;

import lombok.Data;

@Data
public class HomeworkDto {
	String content;
	Integer like;
	Integer studentId;
	Integer taskId;
}
