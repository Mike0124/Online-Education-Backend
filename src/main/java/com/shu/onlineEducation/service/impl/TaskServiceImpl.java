package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.homework.TaskDto;
import com.shu.onlineEducation.common.dto.homework.TaskFileDto;
import com.shu.onlineEducation.dao.CourseChapterJpaRepository;
import com.shu.onlineEducation.dao.TaskFileJpaRepository;
import com.shu.onlineEducation.dao.TaskJpaRepository;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.Task;
import com.shu.onlineEducation.entity.TaskFile;
import com.shu.onlineEducation.service.TaskService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	TaskJpaRepository taskJpaRepository;
	@Autowired
	TaskFileJpaRepository taskFileJpaRepository;
	@Autowired
	CourseChapterJpaRepository courseChapterJpaRepository;
	
	@Override
	public Task getTaskById(Integer taskId) {
		return taskJpaRepository.findByTaskId(taskId);
	}
	
	@Override
	public List<Task> getTaskByCourseChapter(Integer courseId, Integer chapterId) throws NotFoundException {
		CourseChapter courseChapter = courseChapterJpaRepository.findByCourseIdAndChapterId(courseId, chapterId);
		if (courseChapter == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return taskJpaRepository.findByCourseChapter(courseChapter.getCourseChapterPK().getCourseId(), courseChapter.getCourseChapterPK().getChapterId());
	}
	
	@Override
	public void deleteTask(Integer taskId) {
		taskJpaRepository.deleteByTaskId(taskId);
	}
	
	@Override
	public Task addTask(Integer courseId, Integer chapterId, TaskDto taskDto) throws NotFoundException{
		CourseChapter courseChapter = courseChapterJpaRepository.findByCourseIdAndChapterId(courseId, chapterId);
		if (courseChapter == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Task task = new Task();
		task.setCourseId(courseId);
		task.setChapterId(chapterId);
		task.setTaskName(taskDto.getTaskName());
		task.setContent(taskDto.getContent());
		task.setStartTime(DateUtil.stringToTimestamp(taskDto.getStartTime()));
		task.setEndTime(DateUtil.stringToTimestamp(taskDto.getEndTime()));
		taskJpaRepository.saveAndFlush(task);
		return task;
	}
	
	@Override
	public void modifyTask(Integer taskId, TaskDto taskDto) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		if (task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		task.setTaskName(taskDto.getTaskName());
		task.setContent(taskDto.getContent());
		task.setStartTime(DateUtil.stringToTimestamp(taskDto.getStartTime()));
		task.setEndTime(DateUtil.stringToTimestamp(taskDto.getEndTime()));
		taskJpaRepository.saveAndFlush(task);
	}
	
	@Override
	public void addTaskFile(TaskFileDto taskFileDto) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskFileDto.getTaskId());
		if (task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		TaskFile taskFile = new TaskFile();
		taskFile.setTaskId(task.getTaskId());
		taskFile.setFileUrl(taskFileDto.getTaskFileUrl());
		taskFile.setFileName(taskFileDto.getTaskFileName());
		taskFileJpaRepository.saveAndFlush(taskFile);
	}
	
	@Override
	public void deleteTaskFile(Integer taskFileId) {
		taskFileJpaRepository.deleteByTaskFileId(taskFileId);
	}
	
	@Override
	public List<TaskFile> getTaskFileByTask(Integer taskId) throws NotFoundException {
		Task task = taskJpaRepository.findByTaskId(taskId);
		if (task == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return taskFileJpaRepository.findByTask(task.getTaskId());
	}
}
