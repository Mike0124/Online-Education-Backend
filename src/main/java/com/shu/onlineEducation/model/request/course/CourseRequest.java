package com.shu.onlineEducation.model.request.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseRequest {
	Integer teacherId;
	Integer preferId;
	String name;
	String intro;
	String coursePicUrl;
	boolean needVip;
}
