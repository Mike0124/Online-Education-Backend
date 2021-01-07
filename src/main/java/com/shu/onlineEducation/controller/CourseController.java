package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Course")
@Api(tags = "3-课程模块")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private CourseService courseService;

    @GetMapping("/getClass")
    @ApiOperation(value = "获取所有课程详情")
    @ResponseBody
    public Iterable<Course> showInfo(){return courseService.getAllCourses();}

    @PostMapping("/getCourseByPreferId")
    @ApiOperation(value = "获取此偏好的所有课程")
    @ResponseBody
    //TODO 缺少返回体Result+异常处理
    public Iterable<Course> getCourseByPreferId(int preferId){return courseService.getAllCoursesByPreferId(preferId);}

    @PostMapping("/updateCourseStatus")
    @ApiOperation(value = "更新课程状态")
    @ResponseBody
    //TODO 缺少异常处理
    public Result updateCourseStatus(int courseId,int status){
        courseService.updateCourseStatusById(courseId,status);
        return Result.success();
    }

    @PostMapping("/deleteCourseById")
    @ApiOperation(value = "删除课程")
    @ResponseBody
    //TODO 缺少返回题+异常处理
    public Result deleteCourseById(int courseId){
        courseService.deleteCourseById(courseId);
        return Result.success();
    }

    @PostMapping("/getAllCourseNeedVip")
    @ApiOperation(value = "获取所有付费课程")
    @ResponseBody
    //TODO 这是个空函数
    public Result getAllCourseNeedVip(){
        return Result.success();
    }

    @PostMapping("/completeCourseInformation")
    @ApiOperation(value = "完善课程信息")
    @ResponseBody
    //TODO 这是个空函数
    public Result completeCourseInformation(){
        return Result.success();
    }

    @PostMapping("/findCourseByTeacherId")
    @ApiOperation(value = "根据老师查找课程信息")
    @ResponseBody
    //TODO 这是个空函数
    public Result findCourseByTeacherId(){
        return Result.success();
    }
}
