package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.idempotent.Idempotent;
import com.shu.onlineEducation.utils.idempotent.IdempotentToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Idempotent")
public class IdempotentController {
	@Autowired
	IdempotentToken idempotentToken;
	
	@PostMapping("/getToken")
	@ApiOperation(value = "获取幂等性token")
	public Result getToken() {
		return Result.success(idempotentToken.generateToken());
	}
	
	@Idempotent
	@PostMapping("/testToken")
	@ApiOperation(value = "测试幂等性token")
	public Result testToken(HttpServletRequest request) throws NotFoundException {
		//TODO：目前只能通过手动调用校验token
//		idempotentToken.verifyToken(request);
		return Result.success();
	}
}
