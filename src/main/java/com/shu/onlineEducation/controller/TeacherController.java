package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.TeacherDto;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.RedisUtils;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/Teacher")
@Api(tags = "2-教师模块")
public class TeacherController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SmsController smsController;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisUtils redisUtils;
	
	@PostMapping("/getTeacherById")
	@ApiOperation(value = "获取当前教师信息")
	@ResponseBody
	public Result findById(@RequestParam("user_id") int userId) {
		return Result.success(teacherService.getTeacherById(userId));
	}
	
	@GetMapping("/getTeacher")
	@ApiOperation(value = "获取所有用户详情")
	@ResponseBody
	public Result showInfo() {
		return Result.success(teacherService.getAllTeachers());
	}
	
	@PostMapping("/checkByPhoneId")
	@ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
	@ResponseBody
	public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
		if (!teacherService.phoneValid(phoneId)) {
			log.info(smsController.sendCode(phoneId));
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addTeacher")
	@ApiOperation(value = "验证码验证成功后在教师表中添加一项")
	@ResponseBody
	public Result add(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password, @RequestParam("code") String code) throws ExistedException {
		if (code.equals(redisUtils.get(phoneId))) {
			teacherService.addUser(phoneId, password);
			log.info("添加用户成功");
			return Result.success();
		} else {
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
	}
	
	@PostMapping("/loginByPassword")
	@ApiOperation(value = "用户登录")
	@ResponseBody
	public Result loginByPassword(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password)
			throws NotFoundException, ParamErrorException {
		Teacher teacher = teacherService.loginByPassword(phoneId, password);
		logger.info("登录成功");
		return Result.success(teacher);
	}
	
	
	@PostMapping("/deleteTeacherById")
	@ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "删除教师")
	@ResponseBody
	public Result delete(@RequestParam("user_id") int userId) throws NotFoundException {
		teacherService.deleteTeacherById(userId);
		logger.info("删除用户：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/completeTeacherById")
	@ApiOperation(value = "完善教师信息")
	@ResponseBody
	public Result complete(@RequestParam("user_id") Integer userId, @RequestBody TeacherDto teacherDto) throws NotFoundException {
		teacherService.completeTeacherInfo(userId, teacherDto);
		logger.info("完善教师信息：id=" + userId);
		return Result.success();
	}
	
}