package com.shu.onlineEducation.common.dto;

import lombok.Data;

@Data
public class StudentDto {
	String nickname;
	String sex;
	String school;
	Integer majorId;
	Integer grade;
	String picUrl;
}
