package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.homework.HomeworkDto;
import com.shu.onlineEducation.service.HomeworkService;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Homework")
@Api(tags = "7-作业模块")
public class HomeworkController {
	@Autowired
	private HomeworkService homeworkService;
	
	//学生、教师、管理员
	@PostMapping("/getByTaskIdAndStudentId")
	@ApiOperation(value = "根据任务和学生获取作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	//TODO 这是个空函数
	public Result getByTaskIdAndStudentId(Integer taskId, Integer studentId) {
		return Result.success(homeworkService.getByTaskIdAndStudentId(taskId, studentId));
	}
	
	@PostMapping("/addHomeWork")
	@ApiOperation(value = "添加作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	//TODO 这是个空函数
	public Result addHomeWork(HomeworkDto homeworkDto) {
		homeworkService.addHomeWork(homeworkDto);
		return Result.success();
	}
	
	@PostMapping("/deleteHomeWork")
	@ApiOperation(value = "删除作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	//TODO 这是个空函数
	public Result deleteHomeWork(Integer homeworkId) {
		homeworkService.deleteHomeWork(homeworkId);
		return Result.success();
	}
	
	//教师、管理员
	@PostMapping("/getByTaskId")
	@ApiOperation(value = "根据任务获取作业，按提交时间最新排序")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	//TODO 这是个空函数
	public Result getByTaskId(Integer page, Integer taskId) {
		return Result.success();
	}
	
}
