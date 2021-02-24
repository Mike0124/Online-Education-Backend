package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.homework.TaskDto;
import com.shu.onlineEducation.common.dto.homework.TaskFileDto;
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
	
	@PostMapping("getTaskById")
	@ApiOperation(value = "获取当前任务信息")
	@ResponseBody
	public Result getTaskById(Integer taskId) {
		return Result.success(taskService.getTaskById(taskId));
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
	public Result addTask(Integer courseId, Integer chapterId, @RequestBody TaskDto taskDto, @RequestHeader("Authorization") String jwt) throws NotFoundException{
		return Result.success(taskService.addTask(courseId, chapterId, taskDto));
	}
	
	@PostMapping("/modifyTaskById")
	@ApiOperation(value = "修改任务")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result modifyTaskById(Integer taskId, @RequestBody TaskDto taskDto, @RequestHeader("Authorization") String jwt) throws NotFoundException{
		taskService.modifyTask(taskId, taskDto);
		return Result.success();
	}
	
	@PostMapping("/deleteTaskById")
	@ApiOperation(value = "删除任务")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteTaskById(Integer taskId, @RequestHeader("Authorization") String jwt) {
		taskService.deleteTask(taskId);
		return Result.success();
	}
	
	@PostMapping("/addTaskFileByTask")
	@ApiOperation(value = "根据任务添加任务文件")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addTaskFile(@RequestBody TaskFileDto taskFileDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		taskService.addTaskFile(taskFileDto);
		return Result.success();
	}
	
	@PostMapping("/addTaskFileByTasks")
	@ApiOperation(value = "根据任务添加多个任务文件")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addTaskFile(@RequestBody TaskFileDto[] taskFileDtos, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		for (TaskFileDto taskFileDto : taskFileDtos) {
			taskService.addTaskFile(taskFileDto);
		}
		return Result.success();
	}
	
	@PostMapping("/deleteTaskFileById")
	@ApiOperation(value = "删除任务文件")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteTaskFileById(Integer taskFileId, @RequestHeader("Authorization") String jwt) {
		taskService.deleteTaskFile(taskFileId);
		return Result.success();
	}
}
