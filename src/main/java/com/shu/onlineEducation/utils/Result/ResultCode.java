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
    COURSE_LIVE_EXISTED(3005,"此时间段已被预约满"),
    COURSE_LIVE_NOT_FOUND(3006,"此直播不存在"),
    //      专业错误: 4001-4999
    MAJOR_HAS_EXISTED(4001, "专业已存在"),
    MAJOR_NOT_FOUND(4002,"专业不存在"),
    //      作业错误: 5001-5999
    TASK_HAS_CLOSED(5001,"任务已过期"),
    TEACHER_HAS_CORRECTED(5002,"教师已批改"),
    //      直播地址错误: 6001-6999
    LIVE_ADDRESS_HAS_EXISTED(6001,"直播地址已存在"),
    LIVE_ADDRESS_NOT_FOUND(6002,"直播地址不存在");
    

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
