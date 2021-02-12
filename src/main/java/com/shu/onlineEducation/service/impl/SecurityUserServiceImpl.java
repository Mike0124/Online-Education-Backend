package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.entity.Admin;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.security.SecurityUser;
import com.shu.onlineEducation.service.AdminService;
import com.shu.onlineEducation.service.SecurityUserService;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 黄悦麒
 * @className SecurityUserServiceImpl
 * @description 校验已解析jwt中的用户信息，并授权
 * @date 2021/2/3
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	@Autowired
	StudentService studentService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	AdminService adminService;
	
	@Override
	public void authenticateAndAuthorize(SecurityUser securityUser) throws NotFoundException, ParamErrorException {
		switch (securityUser.getType()) {//判断用户类型选择service
			case ("student"):
				Student student = studentService.loginByPassword(securityUser.getPhoneId(), securityUser.getPassword());
				List<String> studentRoles = new ArrayList<>();
				studentRoles.add("ROLE_STUDENT");
				if (student.isVip()) {//若学生是vip则授予vip权限
					studentRoles.add("STUDENT_VIP");
				}
				securityUser.setRoles(studentRoles);
				break;
			case ("teacher"):
				Teacher teacher = teacherService.loginByPassword(securityUser.getPhoneId(), securityUser.getPassword());
				List<String> teacherRoles = new ArrayList<>();
				teacherRoles.add("ROLE_TEACHER");
				securityUser.setRoles(teacherRoles);
				break;
			case ("admin"):
				Admin admin = adminService.loginByPassword(securityUser.getPhoneId(),securityUser.getPassword());
				List<String> adminRoles = new ArrayList<>();
				adminRoles.add("ROLE_ADMIN");
				securityUser.setRoles(adminRoles);
				break;
			default:
				throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
	}
}
