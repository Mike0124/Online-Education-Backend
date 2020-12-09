package com.shu.online_education.Controller;

import com.shu.online_education.utils.Enums.Result;
import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.utils.Enums.ResultCode;
import com.shu.online_education.Service.StudentService;
import com.shu.online_education.utils.ExceptionUtil.ClassHasEnrolledException;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import com.shu.online_education.utils.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Student")
@Api(tags = "学生模块", description = "学生相关模块接口")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/getStudent")
	@ApiOperation(value = "获取所有用户详情")
	@ResponseBody
	public Iterable<StudentInfo> showInfo() {
		return studentService.getAllStudents();
	}
	
	@PostMapping("/checkByPhoneId")
	@ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
	@ResponseBody
	public Result check_phone_id(@RequestParam("phone_id") String phone_id) {
		if (studentService.phone_valid(phone_id)) {
			//TODO 向手机发送短信验证码
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addStudent")
	@ApiOperation(value = "验证码验证成功后在学生表中添加一项")
	@ResponseBody
	public Result add(@RequestParam("phone_id") String phone_id, @RequestParam("password") String password) throws UserHasExistedException {
		studentService.addUser(phone_id, password);
		logger.info("添加用户成功");
		return Result.success();
	}
	
	@PostMapping("/deleteStudentById")
	@ApiOperation(value = "删除学生")
	@ResponseBody
	public Result delete(@RequestParam("user_id") int user_id) throws UserNotFoundException {
		studentService.deleteStudentById(user_id);
		logger.info("删除用户：id=" + user_id);
		return Result.success();
	}
	
	@PostMapping("/completeStudentById")
	@ApiOperation(value = "通过用户Id完善学生信息")
	@ResponseBody
	public Result complete(@RequestParam("user_id") int user_id, @RequestParam("nickname") String nickname,
						   @RequestParam("sex") String sex, @RequestParam("school") String school,
						   @RequestParam("major") String major, @RequestParam("grade") int grade)
			throws UserNotFoundException {
		studentService.completeStudentInfo(user_id, nickname, sex, school, major, grade);
		logger.info("完善学生信息：id=" + user_id);
		return Result.success();
	}
	
	@PostMapping("/collectPreferences")
	@ApiOperation(value = "收集学生偏好")
	@ResponseBody
	public Result CollectPreference(@RequestParam("user_id") int user_id, @RequestParam("prefers") int[] prefers_id) {
		studentService.collectPreference(user_id, prefers_id);
		logger.info("收集到学生偏好：preferences=" + prefers_id);
		return Result.success();
	}
	
	
	@PostMapping("/enrollClass")
	@ApiOperation(value = "学生通过课程id报名课程")
	@ResponseBody
	public Result enroll(@RequestParam("user_id") int user_id, @RequestParam("class_id") int class_id)
			throws ClassHasEnrolledException {
		studentService.enrollClassById(user_id, class_id);
		return Result.success();
	}
}
