package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.homework.CorrectDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkFileDto;
import com.shu.onlineEducation.dao.HomeworkFileJpaRepository;
import com.shu.onlineEducation.dao.HomeworkJpaRepository;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.dao.TaskJpaRepository;
import com.shu.onlineEducation.entity.Homework;
import com.shu.onlineEducation.entity.HomeworkFile;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.Task;
import com.shu.onlineEducation.service.HomeworkService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.utils.status.HomeworkStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public
class HomeworkServiceImpl implements HomeworkService {
	@Autowired
	private HomeworkJpaRepository homeworkJpaRepository;
	@Autowired
	private HomeworkFileJpaRepository homeworkFileJpaRepository;
	@Autowired
	private TaskJpaRepository taskJpaRepository;
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	
	@Override
	public Homework getByTaskAndStudent(Integer taskId, Integer studentId) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return homeworkJpaRepository.findByTaskAndStudent(task, student);
	}
	
	@Override
	public Page<Homework> getByStudent(Pageable pageable, Integer studentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return homeworkJpaRepository.findByStudent(pageable, student);
	}
	
	@Override
	public Optional<Homework> getById(Integer homeworkId) {
		return homeworkJpaRepository.findById(homeworkId);
	}
	
	@Override
	public Page<Homework> getByTask(Pageable pageable, Integer taskId) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		if (task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return homeworkJpaRepository.findByTask(pageable, task);
	}
	
	@Override
	public Page<Homework> getByTaskAndStatus(Pageable pageable, Integer taskId, Integer status) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		if (task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return homeworkJpaRepository.findByTaskAndStatus(pageable, task, status);
	}
	
	@Override
	public List<HomeworkFile> getFilesByHomework(Integer homeworkId) throws NotFoundException {
		Homework homework = homeworkJpaRepository.findByHomeworkId(homeworkId);
		if (homework == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return homeworkFileJpaRepository.findByHomework(homework);
	}
	
	@Override
	public void studentHomework(HomeworkDto homeworkDto) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(homeworkDto.getTaskId());
		Student student = studentJpaRepository.findByUserId(homeworkDto.getStudentId());
		if (student == null || task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Homework homework = homeworkJpaRepository.findByTaskAndStudent(task, student);
		if (homework == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		} else {
			//已批改过的作业不能修改
			if (homework.getStatus() == HomeworkStatus.HAS_CORRECTED) {
				throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
			} else if (homeworkDto.getStatus() == HomeworkStatus.HAS_UPLOADED) {
				//设置提交时间
				homework.setCommitTime(DateUtil.getNowTimeStamp());
			}
			homework.setStatus(homeworkDto.getStatus());
		}
		homework.setContent(homeworkDto.getContent());
		homeworkJpaRepository.saveAndFlush(homework);
	}
	
	@Override
	public Homework addHomework(Integer studentId, Integer taskId) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Homework homework = homeworkJpaRepository.findByTaskAndStudent(task, student);
		if (homework == null) {
			homework = new Homework();
			homework.setStudentId(studentId);
			homework.setTaskId(taskId);
			homework.setStatus(0);
			homework.setLikes(0);
		} else {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		homeworkJpaRepository.saveAndFlush(homework);
		return homework;
	}
	
	@Override
	public void teacherHomework(CorrectDto correctDto) throws NotFoundException {
		Homework homework = homeworkJpaRepository.findByHomeworkId(correctDto.getHomeworkId());
		//只能批改已上传的作业
		if (homework == null || homework.getStatus() != HomeworkStatus.HAS_UPLOADED) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		} else {
			if (correctDto.getMark() != null) {
				homework.setMark(correctDto.getMark());
			}
			homework.setReply(correctDto.getReply());
			homework.setStatus(correctDto.getStatus());
		}
		homeworkJpaRepository.saveAndFlush(homework);
	}
	
	@Override
	public void deleteHomework(Integer homeworkId) {
		homeworkJpaRepository.deleteByHomeworkId(homeworkId);
	}
	
	@Override
	public void addHomeworkFile(HomeworkFileDto homeworkFileDto) throws NotFoundException {
		Homework homework = homeworkJpaRepository.findByHomeworkId(homeworkFileDto.getHomeworkId());
		if (homework == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		HomeworkFile homeworkFile = new HomeworkFile();
		homeworkFile.setHomeworkId(homework.getHomeworkId());
		homeworkFile.setFileUrl(homeworkFileDto.getHomeworkFileUrl());
		homeworkFile.setFileName(homeworkFileDto.getHomeworkFileName());
		homeworkFileJpaRepository.saveAndFlush(homeworkFile);
	}
	
	@Override
	public void deleteHomeworkFile(Integer homeworkFileId) {
		homeworkFileJpaRepository.deleteByHomeworkFileId(homeworkFileId);
	}
}
