package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.homework.HomeworkDto;

public interface HomeworkService {
	Object getByTaskIdAndStudentId(Integer taskId, Integer studentId);
	
	void addHomeWork(HomeworkDto homeworkDto);
	
	void deleteHomeWork(Integer homeworkId);
}

