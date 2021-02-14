package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.course.TaskDto;
import com.shu.onlineEducation.service.TaskService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/Task")
@Api(tags = "5-任务模块")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	//学生、教师、管理员、游客
	@PostMapping("getTaskByCourseChapter")
	@ApiOperation(value = "根据课程章节获取任务")
	@ResponseBody
	public Result getTaskByCourseChapter(Integer courseId, Integer chapterId) throws NotFoundException {
		return Result.success(taskService.getTaskByCourseChapter(courseId, chapterId));
	}
	
	@PostMapping("getTaskFileByTask")
	@ApiOperation(value = "根据任务获取任务文件")
	@ResponseBody
	public Result getTaskFileByTask(Integer taskId) throws NotFoundException {
		return Result.success(taskService.getTaskFileByTask(taskId));
	}
	
	//教师、管理员
	@PostMapping("/addTaskByCourseChapter")
	@ApiOperation(value = "根据课程章节添加任务")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addTask(Integer courseId, Integer chapterId, @RequestBody TaskDto taskDto) throws NotFoundException, ParseException {
		taskService.addTask(courseId, chapterId, taskDto);
		return Result.success();
	}
	
	@PostMapping("/deleteTaskById")
	@ApiOperation(value = "删除任务")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteTaskById(Integer taskId) {
		taskService.deleteTask(taskId);
		return Result.success();
	}
	
	@PostMapping("/addTaskFileByTask")
	@ApiOperation(value = "根据任务添加任务文件")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addTaskFile(Integer taskId, String taskFileUrl) throws NotFoundException {
		taskService.addTaskFile(taskId, taskFileUrl);
		return Result.success();
	}
	
	@PostMapping("/deleteTaskFileById")
	@ApiOperation(value = "删除任务文件")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteTaskFileById(Integer taskFileId) {
		taskService.deleteTaskFile(taskFileId);
		return Result.success();
	}
}
