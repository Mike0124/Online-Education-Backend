package com.shu.onlineEducation.utils.ExceptionUtil;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException() {
        super();
    }

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
