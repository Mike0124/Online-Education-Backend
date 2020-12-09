package com.shu.online_education.utils.ExceptionUtil;

public class ClassNotFoundException extends Exception {
    public ClassNotFoundException() {
        super();
    }

    public ClassNotFoundException(String message) {
        super(message);
    }

    public ClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
