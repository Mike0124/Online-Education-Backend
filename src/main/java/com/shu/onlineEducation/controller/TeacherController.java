package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.LoginDto;
import com.shu.onlineEducation.common.dto.RegisterDto;
import com.shu.onlineEducation.common.dto.TeacherDto;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.CourseCommentService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.MapUtil;
import com.shu.onlineEducation.utils.RedisUtil;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/Teacher")
@Api(tags = "2-教师模块")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SmsController smsController;
	@Autowired
	private CourseCommentService courseCommentService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisUtil redisUtil;
	
	@PostMapping("/getTeacherById")
	@ApiOperation(value = "获取当前教师信息")
//	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result findById(@RequestParam("user_id") int userId) {
		return Result.success(teacherService.getTeacherById(userId));
	}
	
	@GetMapping("/getTeacher")
	@ApiOperation(value = "获取所有用户详情")
//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result showInfo() {
		return Result.success(teacherService.getAllTeachers());
	}
	
	@PostMapping("/getTeacherByStatus0")
	@ApiOperation(value = "获取待审核的教师信息")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result findById(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestHeader("Authorization") String jwt) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10);
		return Result.success(MapUtil.pageResponse(teacherService.getTeacherByStatus(pageable,0)));
	}
	
	@PostMapping("/checkByPhoneId")
//	@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
	@ResponseBody
	public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
		if (!teacherService.phoneValid(phoneId)) {
			smsController.sendCode(phoneId);
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addTeacher")
	@ApiOperation(value = "验证码验证成功后在教师表中添加一项")
	@ResponseBody
	public Result add(@RequestBody RegisterDto registerDto) throws ExistedException {
		if (registerDto.getCode().equals(redisUtil.get(registerDto.getPhone()))) {
			teacherService.addUser(registerDto.getPhone(), registerDto.getPassword());
			return Result.success();
		} else {
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
	}
	
	@PostMapping("/loginByPassword")
	@ApiOperation(value = "用户登录")
	@ResponseBody
	public Result loginByPassword(@RequestBody LoginDto loginDto, HttpServletResponse response) throws NotFoundException, ParamErrorException {
		Teacher teacher = teacherService.loginByPassword(loginDto.getPhone(), loginDto.getPassword());
		String jwt = jwtUtil.generateToken(teacher.getPhoneId(), teacher.getPassword(), "teacher");
		response.setHeader("Authorization", jwt);
		response.setHeader("Access-control-Expose-Headers", "Authorization");
		log.info("登录成功");
		return Result.success(teacher);
	}
	
	
	@PostMapping("/deleteTeacherById")
	@ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "删除教师")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result delete(@RequestParam("user_id") int userId, @RequestHeader("Authorization") String jwt) {
		teacherService.deleteTeacherById(userId);
		return Result.success();
	}
	
	@PostMapping("/completeTeacherById")
	@ApiOperation(value = "完善教师信息")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result complete(@RequestParam("user_id") Integer userId, @RequestBody TeacherDto teacherDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		teacherService.completeTeacherInfo(userId, teacherDto);
		return Result.success();
	}
	
	@PostMapping("/updateTeacherStatus")
	@ApiOperation(value = "认证/驳回教师状态 1.认证成功  2.驳回")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result updateCourseStatus(@RequestParam("user_id") Integer userId, Integer status, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		teacherService.updateTeacherStatusById(userId, status);
		return Result.success();
	}
	
	@PostMapping("/analysisCommentByCourse")
	@ApiOperation(value = "获取课程评论分析")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result analysisCommentByCourse(@RequestParam("course_id") Integer courseId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(courseCommentService.getResultByCourse(courseId));
	}
	
	@PostMapping("/getCommentByCourseWithRegex")
	@ApiOperation(value = "正则搜索课程评论")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getCommentByCourseWithRegex(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam("course_id") Integer courseId, String query, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "time"));
		return Result.success(MapUtil.pageResponse(courseCommentService.getCommentsByCourseWithRegex(pageable, courseId, query)));
	}
}