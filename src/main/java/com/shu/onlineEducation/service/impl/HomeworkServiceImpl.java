package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.homework.HomeworkDto;
import com.shu.onlineEducation.dao.HomeworkFileJpaRepository;
import com.shu.onlineEducation.dao.HomeworkJpaRepository;
import com.shu.onlineEducation.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HomeworkServiceImpl implements HomeworkService {
	@Autowired
	private HomeworkJpaRepository homeworkJpaRepository;
	@Autowired
	private HomeworkFileJpaRepository homeworkFileJpaRepository;
	
	@Override
	public Object getByTaskIdAndStudentId(Integer taskId, Integer studentId) {
		return null;
	}
	
	@Override
	public void addHomeWork(HomeworkDto homeworkDto) {
	
	}
	
	@Override
	public void deleteHomeWork(Integer homeworkId) {
	
	}
}
