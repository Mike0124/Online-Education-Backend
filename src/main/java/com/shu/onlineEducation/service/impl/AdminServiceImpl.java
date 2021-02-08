package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.AdminJpaRepository;
import com.shu.onlineEducation.entity.Admin;
import com.shu.onlineEducation.service.AdminService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminJpaRepository adminJpaRepository;
	
	@Override
	public Admin loginByPassword(String phone, String password) throws NotFoundException, ParamErrorException {
		Admin admin = adminJpaRepository.findByPhoneId(phone);
		if (admin == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (!password.equals(admin.getPassword())) {
			throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
		}
		return admin;
	}
}
