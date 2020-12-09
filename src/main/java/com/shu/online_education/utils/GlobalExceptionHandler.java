package com.shu.online_education.utils;

import com.shu.online_education.utils.Enums.Result;
import com.shu.online_education.utils.Enums.ResultCode;
import com.shu.online_education.utils.ExceptionUtil.ClassHasEnrolledException;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 找不到用户
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public Result userNotFoundException(UserNotFoundException e){
        logger.error("用户没找到：【" + e.getMessage() + "】");
        return Result.failure(ResultCode.USER_NOT_EXIST);
    }

    /**
     * 用户已存在
     */
    @ExceptionHandler(UserHasExistedException.class)
    @ResponseBody
    public Result userHasExistedException(UserHasExistedException e){
        logger.error("用户已存在：【" + e.getMessage() + "】");
        return Result.failure(ResultCode.USER_HAS_EXISTED);
    }

    /**
     * 找不到课程
     */
    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseBody
    public Result classNotFoundException(ClassNotFoundException e){
        logger.error("课程不存在：【" + e.getMessage() + "】");
        return Result.failure(ResultCode.CLASS_NOT_EXIST);
    }

    /**
     * 该学生已报名此课程
     */
    @ExceptionHandler(ClassHasEnrolledException.class)
    @ResponseBody
    public Result classHasEnrolledException(ClassHasEnrolledException e){
        logger.error("该学生已报名此课程：【" + e.getMessage() + "】");
        return Result.failure(ResultCode.CLASS_HAS_ENROLLED);
    }
}
