package com.shu.onlineEducation.utils.ExceptionUtil;

public class CourseHasEnrolledException extends Exception {
    public CourseHasEnrolledException() {
        super();
    }

    public CourseHasEnrolledException(String message) {
        super(message);
    }

    public CourseHasEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }
}
