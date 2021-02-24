package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.homework.CorrectDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkFileDto;
import com.shu.onlineEducation.entity.Homework;
import com.shu.onlineEducation.entity.HomeworkFile;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeworkService {
	Homework getByTaskAndStudent(Integer taskId, Integer studentId) throws NotFoundException;
	
	Page<Homework> getByTask(Pageable pageable, Integer taskId) throws NotFoundException;
	
	Page<Homework> getByTaskAndStatus(Pageable pageable, Integer taskId, Integer status) throws NotFoundException;
	
	List<HomeworkFile> getFilesByHomework(Integer homeworkId) throws NotFoundException;
	
	void studentHomework(HomeworkDto homeworkDto) throws NotFoundException;
	
	Homework addHomework(Integer studentId, Integer taskId) throws NotFoundException;
	
	void teacherHomework(CorrectDto correctDto) throws NotFoundException;
	
	void deleteHomework(Integer homeworkId);
	
	void addHomeworkFile(HomeworkFileDto homeworkFileDto) throws NotFoundException;
	
	void deleteHomeworkFile(Integer homeworkId);
}

