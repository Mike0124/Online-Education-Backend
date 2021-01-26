package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.RedisUtil;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/Student")
@Api(tags = "1-学生模块")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SmsController smsController;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	RedisUtil redisUtil;
	
	@GetMapping("/getStudent")
	@ApiOperation(value = "获取所有用户详情")
	@ResponseBody
	public Iterable<Student> showInfo() {
		return studentService.getAllStudents();
	}
	
	@PostMapping("/checkByPhoneId")
//	@ApiImplicitParam(name = "phone_id",value = "手机号", required = true,paramType = "form",dataType = "String")
	@ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
	@ResponseBody
	public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
		if (!studentService.phoneValid(phoneId)) {
			log.info(smsController.sendCode(phoneId));
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addStudent")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form",dataType = "String"),
//			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
//	})
	@ApiOperation(value = "验证码验证成功后在学生表中添加一项")
	@ResponseBody
	public Result add(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password, @RequestParam("code") String code) throws ExistedException {
		if(code.equals(redisUtil.get(phoneId))){
			studentService.addUser(phoneId, password);
			log.info("添加用户成功");
			return Result.success();
		}else{
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
		
	}
	
	@PostMapping("/loginByPassword")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String"),
//			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
//	})
	@ApiOperation(value = "用户登录")
	@ResponseBody
	public Result loginByPassword(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password, HttpServletResponse response)
			throws NotFoundException, ParamErrorException {
		Student student = studentService.loginByPassword(phoneId, password);
		String jwt = jwtUtil.generateToken(student.getUserId());
		response.setHeader("Authorization", jwt);
		response.setHeader("Access-control-Expose-Headers", "Authorization");
		log.info("登录成功");
		return Result.success(student);
	}
	
	@PostMapping("/deleteStudentById")
	@ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "删除学生")
	@ResponseBody
	public Result delete(@RequestParam("user_id") int userId) throws NotFoundException {
		studentService.deleteStudentById(userId);
		log.info("删除用户：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/commentCourseByCourseId")
	@ApiOperation(value = "根据课程Id对课程进行评价")
	@ResponseBody
	public Result comment(@RequestBody CourseCommentDto courseCommentDto) throws NotFoundException {
		studentService.commentCourseByCourseId(courseCommentDto.getComment(), courseCommentDto.getCommentMark(),
				courseCommentDto.getCourseId(), courseCommentDto.getStudentId());
		return Result.success();
	}
	
	@PostMapping("/completeStudentById")
	@ApiOperation(value = "通过用户Id完善学生信息")
	@ResponseBody
	public Result complete(@RequestParam("user_id") int userId, @RequestParam("nickname") String nickname,
						   @RequestParam("sex") String sex, @RequestParam("school") String school,
						   @RequestParam("major_id") int majorId, @RequestParam("grade") int grade)
			throws NotFoundException {
		studentService.completeStudent(userId, nickname, sex, school, majorId, grade);
		log.info("完善学生信息：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/collectPreferences")
	@ApiOperation(value = "收集学生偏好")
	@ResponseBody
	public Result collectPreference(@RequestParam("user_id") int userId, @RequestParam("prefers") int[] prefersId) {
		studentService.collectPreference(userId, prefersId);
		return Result.success();
	}
	
	@PostMapping("/findAllPreferences")
	@ApiOperation(value = "返回所有当前学生的偏好")
	@ResponseBody
	public Result findAllPreferences(@RequestParam("user_id") int userId) {
		return Result.success(studentService.findAllPreferences(userId));
	}
	
}
