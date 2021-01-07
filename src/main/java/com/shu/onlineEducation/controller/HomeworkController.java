package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Homework")
@Api(tags = "7-作业模块")
public class HomeworkController {
    @PostMapping("/findCourseByTeacherId")
    @ApiOperation(value = "根据老师查找课程信息")
    @ResponseBody
    //TODO 这是个空函数
    public Result findCourseByTeacherId(){
        return Result.success();
    }
}
