package com.shu.online_education.Controller;

import com.shu.online_education.Entity.ClassInfo;
import com.shu.online_education.Service.ClassService;
import com.shu.online_education.utils.GlobalExceptionHandler;
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
@Api(tags = "课程模块", description = "课程相关模块接口")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ClassService classService;

    @GetMapping("/GetClass")
    @ApiOperation(value = "获取所有课程详情")
    @ResponseBody
    public Iterable<ClassInfo> showInfo(){return classService.getAllClasses();}
}
