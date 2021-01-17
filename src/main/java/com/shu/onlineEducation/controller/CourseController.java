package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.CoursePreferNotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.CourseVipNotFoundException;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Course")
@Api(tags = "3-课程模块")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    AppProperties appProperties;

    @GetMapping("/getClass")
    @ApiOperation(value = "获取所有课程详情")
    @ResponseBody
    public Iterable<Course> showInfo(){return courseService.getAllCourses();}

    @PostMapping("/getCourseByPreferId")
    @ApiOperation(value = "获取此偏好的所有课程,1表示按时间最新排序，2表示按课程评分排序，3表示按课程观看数量排序")
    @ResponseBody
    public Result getCourseByPreferId(int page, int sort, int preferId) throws CoursePreferNotFoundException{
        if (sort == 2){
            Sort sort_tmp = Sort.by(Sort.Direction.DESC,"courseAvgMark");
            Pageable pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), sort_tmp);
            return Result.success(courseService.getAllCoursesByPreferId(pageable,preferId));
        }
        return Result.failure(ResultCode.PARAM_IS_INVALID);
    }

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
    public Result getAllCourseNeedVip() throws CourseVipNotFoundException {
        return Result.success(courseService.getAllCoursesByNeedVip(true));
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
