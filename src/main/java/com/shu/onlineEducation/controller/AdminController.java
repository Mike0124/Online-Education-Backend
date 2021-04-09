package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.LoginDto;
import com.shu.onlineEducation.entity.Admin;
import com.shu.onlineEducation.service.AdminService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/Admin")
@Api(tags = "管理员模块")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/loginByPassword")
	@ApiOperation(value = "管理员登录，登录名和密码都是admin")
	@ResponseBody
	public Result loginByPassword(@RequestBody LoginDto loginDto, HttpServletResponse response) throws NotFoundException, ParamErrorException {
		Admin admin = adminService.loginByPassword(loginDto.getPhone(), loginDto.getPassword());
		String jwt = jwtUtil.generateToken(admin.getPhoneId(), admin.getPassword(), "admin");
		response.setHeader("Authorization", jwt);
		response.setHeader("Access-control-Expose-Headers", "Authorization");
		log.info("管理员登录成功");
		return Result.success(admin);
	}
}
