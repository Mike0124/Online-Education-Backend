package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Admin;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;

public interface AdminService {
	Admin loginByPassword(String phone, String password) throws NotFoundException, ParamErrorException;
}
