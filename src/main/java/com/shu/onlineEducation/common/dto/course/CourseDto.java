package com.shu.onlineEducation.common.dto.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseDto {
	Integer teacherId;
	Integer preferId;
	String name;
	String intro;
	String coursePicUrl;
	boolean needVip;
}
