package com.shu.onlineEducation.common.dto.course;

import lombok.Data;

@Data
public class WatchRecordDto {
	Integer studentId;
	Integer courseId;
	Integer chapterId;
	Integer videoId;
}
