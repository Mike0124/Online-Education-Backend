package com.shu.onlineEducation.common.dto.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseChapterVideoDto {
	Integer videoId;
	String videoUrl;
	String videoName;
}
