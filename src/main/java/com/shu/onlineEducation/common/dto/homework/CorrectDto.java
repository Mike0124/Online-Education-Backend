package com.shu.onlineEducation.common.dto.homework;

import lombok.Data;

@Data
public class CorrectDto {
	Integer homeworkId;
	Integer mark;
	String reply;
	Integer status;
}
