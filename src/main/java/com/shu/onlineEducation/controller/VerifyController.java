package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.utils.idempotent.Idempotent;
import com.shu.onlineEducation.utils.idempotent.IdempotentToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/api/Login")
@Api(tags = "9-校验模块")
public class VerifyController {
	
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	StudentService studentService;
	@Autowired
	TeacherService teacherService;
	
	@PostMapping("/loginByJwt")
	@ApiOperation(value = "通过JWT登录")
	@ResponseBody
	public Result loginByJwt(@RequestHeader("Authorization") String jwt) throws NotFoundException, ParamErrorException {
		if (jwt == null) {
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
		Claims claims;
		try {
			claims = jwtUtil.getClaimByToken(jwt);
		}catch(ExpiredJwtException ex) {//JWT过期
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
		String phone = (String) claims.get("phone");
		String password = (String) claims.get("password");
		switch ((String)claims.get("type")) {//判断用户类型选择service
			case ("student"):
				Student student = studentService.loginByPassword(phone, password);
				return Result.success(student);
			case ("teacher"):
				Teacher teacher = teacherService.loginByPassword(phone, password);
				return Result.success(teacher);
			default:
				throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
	}
	
	@Autowired
	IdempotentToken idempotentToken;
	
	@PostMapping("/getIdempotentToken")
	@ApiOperation(value = "获取幂等性token")
	public Result getToken() {
		return Result.success(idempotentToken.generateToken());
	}
	
	@Idempotent
	@PostMapping("/testToken")
	@ApiOperation(value = "测试幂等性token")
	public Result testToken(HttpServletRequest request) throws NotFoundException {
		idempotentToken.verifyToken(request);
		return Result.success();
	}
}
