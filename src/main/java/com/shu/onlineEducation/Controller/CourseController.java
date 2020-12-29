package com.shu.onlineEducation.Controller;

import com.shu.onlineEducation.Entity.Course;
import com.shu.onlineEducation.Service.CourseService;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Class")
@Api(tags = "课程模块")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private CourseService courseService;

    @GetMapping("/GetClass")
    @ApiOperation(value = "获取所有课程详情")
    @ResponseBody
    public Iterable<Course> showInfo(){return courseService.getAllCourses();}
}
