package com.shu.online_education.Controller;

import com.shu.online_education.utils.Enums.Result;
import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.utils.Enums.ResultCode;
import com.shu.online_education.Service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Student")
@Api(tags = "学生模块", description = "学生相关模块接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudent")
    @ApiOperation(value = "获取所有用户详情")
    @ResponseBody
    public Iterable<StudentInfo> showInfo() {
        return studentService.getAllStudents();
    }

    @PostMapping("/checkByPhoneId")
    @ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
    @ResponseBody
    public Result check_phone_id(@RequestParam("phone_id") String phone_id) {
        if (studentService.phone_valid(phone_id)) {
            //TODO 向手机发送短信验证码
            return Result.success();
        } else {
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
    }
}
