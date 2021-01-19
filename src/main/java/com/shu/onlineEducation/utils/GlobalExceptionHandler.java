package com.shu.onlineEducation.utils;

import com.shu.onlineEducation.utils.ExceptionUtil.*;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 某数据已存在
     */
    @ExceptionHandler(ExistedException.class)
    @ResponseBody
    public Result existedException(ExistedException e){
        logger.error("发生业务异常！(已有该数据)原因是：【"+ e.getMessage() + "】");
        for (ResultCode code :ResultCode.values()){
            if (code.getCode().equals(e.getErrorCode())){
                return Result.failure(code);
            }
        }
        return Result.failure(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 找不到某对象
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public Result notFoundException(NotFoundException e){
        logger.error("发生业务异常！(找不到数据)原因是：【"+ e.getMessage() + "】");
        for (ResultCode code :ResultCode.values()){
            if (code.getCode().equals(e.getErrorCode())){
                return Result.failure(code);
            }
        }
        return Result.failure(ResultCode.PARAM_IS_INVALID);
    }

    
    /**
     * 参数错误
     */
    @ExceptionHandler(ParamErrorException.class)
    @ResponseBody
    public Result paramErrorException(ParamErrorException e){
        logger.error("发生参数错误！原因是：【"+ e.getMessage() + "】");
        for (ResultCode code :ResultCode.values()){
            if (code.getCode().equals(e.getErrorCode())){
                return Result.failure(code);
            }
        }
        return Result.failure(ResultCode.PARAM_IS_INVALID);
    }
}
