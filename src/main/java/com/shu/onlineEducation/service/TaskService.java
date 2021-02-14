package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.course.TaskDto;
import com.shu.onlineEducation.entity.Task;
import com.shu.onlineEducation.entity.TaskFile;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;

import java.text.ParseException;
import java.util.List;

public interface TaskService {
	/*课程章节视频*/
	
	List<Task> getTaskByCourseChapter(Integer courseId, Integer chapterId) throws NotFoundException;
	
	void deleteTask(Integer taskId);
	
	void addTask(Integer courseId, Integer chapterId, TaskDto taskDto) throws NotFoundException, ParseException;
	
	void addTaskFile(Integer taskId, String taskFileUrl) throws NotFoundException;
	
	void deleteTaskFile(Integer taskFileId);
	
	List<TaskFile> getTaskFileByTask(Integer taskId) throws NotFoundException;
}
