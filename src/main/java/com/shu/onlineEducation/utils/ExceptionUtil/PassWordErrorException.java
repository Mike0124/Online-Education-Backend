package com.shu.onlineEducation.utils.ExceptionUtil;

public class PassWordErrorException extends Exception{
	public PassWordErrorException(){super();}
	public PassWordErrorException(String message) {
		super(message);
	}
	
	public PassWordErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
