package com.shu.onlineEducation.utils.ExceptionUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExistedException extends Exception {
    protected Integer errorCode;

    protected String errorMsg;

    public ExistedException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
