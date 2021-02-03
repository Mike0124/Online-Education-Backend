package com.shu.onlineEducation.security.service;

import com.shu.onlineEducation.security.SecurityUser;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;

public interface SecurityUserService {
	void authenticateAndAuthorize(SecurityUser securityUser)throws NotFoundException, ParamErrorException;
}
