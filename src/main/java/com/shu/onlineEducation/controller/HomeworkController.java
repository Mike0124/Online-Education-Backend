package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.homework.CorrectDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkDto;
import com.shu.onlineEducation.common.dto.homework.HomeworkFileDto;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.HomeworkService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.MapUtil;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Homework")
@Api(tags = "7-作业模块")
public class HomeworkController {
	@Autowired
	private HomeworkService homeworkService;
	@Autowired
	private AppProperties appProperties;
	
	@PostMapping("/getByTaskAndStudent")
	@ApiOperation(value = "根据任务和学生获取作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getByTaskIdAndStudentId(Integer taskId, Integer studentId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(homeworkService.getByTaskAndStudent(taskId, studentId));
	}
	
	@PostMapping("/getFilesByHomework")
	@ApiOperation(value = "根据作业获取作业文件")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getFilesByHomework(Integer homeworkId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(homeworkService.getFilesByHomework(homeworkId));
	}
	
	@PostMapping("/addHomework")
	@ApiOperation(value = "学生添加作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addHomework(Integer studentId, Integer taskId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(homeworkService.addHomework(studentId, taskId));
	}
	
	@PostMapping("/studentHomework")
	@ApiOperation(value = "学生修改作业")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result studentHomework(@RequestBody HomeworkDto homeworkDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		homeworkService.studentHomework(homeworkDto);
		return Result.success();
	}
	
	@PostMapping("/teacherHomework")
	@ApiOperation(value = "老师批改/驳回作业")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result teacherHomework(@RequestBody CorrectDto correctDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		homeworkService.teacherHomework(correctDto);
		return Result.success();
	}
	
	@PostMapping("/deleteHomeWork")
	@ApiOperation(value = "删除作业")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result deleteHomework(Integer homeworkId, @RequestHeader("Authorization") String jwt) {
		homeworkService.deleteHomework(homeworkId);
		return Result.success();
	}
	
	@PostMapping("/addHomeworkFile")
	@ApiOperation(value = "添加作业文件")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addHomeworkFile(@RequestBody HomeworkFileDto homeworkFileDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		homeworkService.addHomeworkFile(homeworkFileDto);
		return Result.success();
	}
	
	@PostMapping("/addHomeworkFiles")
	@ApiOperation(value = "添加多个作业文件")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addHomeworkFile(@RequestBody HomeworkFileDto[] homeworkFileDtos, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		for (HomeworkFileDto homeworkFileDto : homeworkFileDtos) {
			homeworkService.addHomeworkFile(homeworkFileDto);
		}
		return Result.success();
	}
	
	@PostMapping("/deleteHomeworkFile")
	@ApiOperation(value = "删除作业文件")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteHomeworkFile(Integer homeworkFileId, @RequestHeader("Authorization") String jwt) {
		homeworkService.deleteHomeworkFile(homeworkFileId);
		return Result.success();
	}
	
	@PostMapping("/getByTask")
	@ApiOperation(value = "根据任务获取作业，提交时间最新")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getByTaskId(Integer page, Integer taskId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "commitTime"));
		return Result.success(MapUtil.pageResponse(homeworkService.getByTask(pageable, taskId)));
	}
	
	@PostMapping("/getByTaskAndStatus")
	@ApiOperation(value = "根据任务和任务状态获取作业，提交时间最新 未上传：0 已驳回：1 已上传：2 已批改：3")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getByTaskAndStatus(Integer page, Integer taskId, Integer status, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "commitTime"));
		return Result.success(MapUtil.pageResponse(homeworkService.getByTaskAndStatus(pageable, taskId, status)));
	}
}
