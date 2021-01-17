package com.shu.onlineEducation.utils.ExceptionUtil;

public class CourseVipNotFoundException extends Exception{
    public CourseVipNotFoundException() {
        super();
    }

    public CourseVipNotFoundException(String message) {
        super(message);
    }

    public CourseVipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
