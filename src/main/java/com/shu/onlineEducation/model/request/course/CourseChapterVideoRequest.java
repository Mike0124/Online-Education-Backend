package com.shu.onlineEducation.model.request.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseChapterVideoRequest {
	String videoUrl;
	String videoName;
}
