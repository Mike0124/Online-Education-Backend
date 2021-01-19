package com.shu.onlineEducation.utils.Result;

public enum ResultCode {
    //    成功状态码
    SUCCESS(1000, "成功"),
    //    参数错误：1001-1999
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_ISI_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    //    用户错误：2001-2999
    USER_HAS_EXISTED(2001, "用户已存在"),
    USER_NOT_EXIST(2002, "用户不存在"),
    USER_LOGIN_ERROR(2003, "密码错误"),
    //    课程错误: 3001-3999
    COURSE_NOT_EXIST(3001,"课程不存在"),
    COURSE_PREFER_NOT_EXIST(3002,"此偏好课程不存在"),
    COURSE_VIP_NOT_EXIST(3003,"VIP课程不存在"),
    COURSE_VIP_PREFER_NOT_EXIST(3004, "此偏好VIP课程不存在"),

    COURSE_HAS_ENROLLED(3101,"该学生已报名该课程");

    //


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
