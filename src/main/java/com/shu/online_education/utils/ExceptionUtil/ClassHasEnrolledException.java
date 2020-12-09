package com.shu.online_education.utils.ExceptionUtil;

public class ClassHasEnrolledException extends Exception {
    public ClassHasEnrolledException() {
        super();
    }

    public ClassHasEnrolledException(String message) {
        super(message);
    }

    public ClassHasEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }
}
