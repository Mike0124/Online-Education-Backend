package com.shu.onlineEducation.utils.ExceptionUtil;

public class CoursePreferNotFoundException extends Exception {
    public CoursePreferNotFoundException() {
        super();
    }

    public CoursePreferNotFoundException(String message) {
        super(message);
    }

    public CoursePreferNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
