package com.shu.onlineEducation.utils.ExceptionUtil;

public class UserHasExistedException extends Exception {
    public UserHasExistedException() {
        super();
    }

    public UserHasExistedException(String message) {
        super(message);
    }

    public UserHasExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
