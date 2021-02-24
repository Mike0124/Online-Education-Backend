package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.homework.TaskDto;
import com.shu.onlineEducation.common.dto.homework.TaskFileDto;
import com.shu.onlineEducation.entity.Task;
import com.shu.onlineEducation.entity.TaskFile;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;

import java.text.ParseException;
import java.util.List;

public interface TaskService {
	Task getTaskById(Integer taskId);
	
	List<Task> getTaskByCourseChapter(Integer courseId, Integer chapterId) throws NotFoundException;
	
	void deleteTask(Integer taskId);
	
	Task addTask(Integer courseId, Integer chapterId, TaskDto taskDto) throws NotFoundException;
	
	void modifyTask(Integer taskId, TaskDto taskDto) throws NotFoundException;
	
	void addTaskFile(TaskFileDto taskFileDto) throws NotFoundException;
	
	void deleteTaskFile(Integer taskFileId);
	
	List<TaskFile> getTaskFileByTask(Integer taskId) throws NotFoundException;
}
