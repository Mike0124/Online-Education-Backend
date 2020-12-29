package com.shu.onlineEducation.Controller;

import com.shu.onlineEducation.utils.Enums.Result;
import com.shu.onlineEducation.Entity.Student;
import com.shu.onlineEducation.utils.Enums.ResultCode;
import com.shu.onlineEducation.Service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.CourseHasEnrolledException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;

@RestController
@RequestMapping("/api/Student")
@Api(tags = "学生模块")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/getStudent")
	@ApiOperation(value = "获取所有用户详情")
	@ResponseBody
	public Iterable<Student> showInfo() {
		return studentService.getAllStudents();
	}
	
	@GetMapping("/checkByPhoneId")
	@ApiImplicitParam(name = "phone_id",value = "手机号", required = true,paramType = "query",dataType = "String")
	@ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
	@ResponseBody
	public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
		if (!studentService.phoneValid(phoneId)) {
			//TODO 向手机发送短信验证码
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addStudent")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form",dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
	})
	@ApiOperation(value = "验证码验证成功后在学生表中添加一项")
	@ResponseBody
	public Result add(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password) throws UserHasExistedException {
		studentService.addUser(phoneId, password);
		logger.info("添加用户成功");
		return Result.success();
	}

	@PostMapping("/loginByPassword")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
	})
	@ApiOperation(value = "用户登录")
	@ResponseBody
	public Result loginByPassword(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password) throws Exception {
		Student student = studentService.loginByPassword(phoneId,password);
		logger.info("登录成功");
		return Result.success(student);
	}
	
	@PostMapping("/deleteStudentById")
	@ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "删除学生")
	@ResponseBody
	public Result delete(@RequestParam("user_id") int userId) throws UserNotFoundException {
		studentService.deleteStudentById(userId);
		logger.info("删除用户：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/completeStudentById")
	@ApiOperation(value = "通过用户Id完善学生信息")
	@ResponseBody
	public Result complete(@RequestParam("user_id") int userId, @RequestParam("nickname") String nickname,
						   @RequestParam("sex") String sex, @RequestParam("school") String school,
						   @RequestParam("major_id") int majorId, @RequestParam("grade") int grade)
			throws UserNotFoundException {
		studentService.completeStudent(userId, nickname, sex, school, majorId, grade);
		logger.info("完善学生信息：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/collectPreferences")
	@ApiOperation(value = "收集学生偏好")
	@ResponseBody
	public Result collectPreference(@RequestParam("user_id") int userId, @RequestParam("prefers") int[] prefersId) {
		studentService.collectPreference(userId, prefersId);
		logger.info("收集到学生偏好：preferences=" + prefersId);
		return Result.success();
	}
	
	
	@PostMapping("/enrollClass")
	@ApiOperation(value = "学生通过课程id报名课程")
	@ResponseBody
	public Result enroll(@RequestParam("user_id") int userId, @RequestParam("class_id") int classId)
			throws CourseHasEnrolledException {
		studentService.enrollCourseById(userId, classId);
		return Result.success();
	}
}
