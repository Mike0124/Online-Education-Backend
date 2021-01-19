package com.shu.onlineEducation.utils.ExceptionUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotFoundException extends Exception {

    protected Integer errorCode;

    protected String errorMsg;

    public NotFoundException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
