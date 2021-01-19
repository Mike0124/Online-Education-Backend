package com.shu.onlineEducation.utils.ExceptionUtil;

import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NotFoundException extends Exception {
	
	protected Integer errorCode;
	
	protected String errorMsg;
	
	public NotFoundException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.errorCode = resultCode.getCode();
		this.errorMsg = resultCode.getMessage();
	}
	
	public NotFoundException(ResultCode resultCode, Throwable cause) {
		super(resultCode.getMessage(), cause);
		this.errorCode = resultCode.getCode();
		this.errorMsg = resultCode.getMessage();
	}
}
