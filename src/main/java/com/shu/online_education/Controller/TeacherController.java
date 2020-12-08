package com.shu.online_education.Controller;

import com.shu.online_education.Entity.TeacherInfo;
import com.shu.online_education.utils.Enums.Result;
import com.shu.online_education.utils.Enums.ResultCode;
import com.shu.online_education.Service.TeacherService;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import com.shu.online_education.utils.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Teacher")
@Api(tags = "教师模块", description = "教师相关模块接口")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeacher")
    @ApiOperation(value = "获取所有用户详情")
    @ResponseBody
    public Iterable<TeacherInfo> showInfo() {
        return teacherService.getAllTeachers();
    }

    @PostMapping("/checkByPhoneId")
    @ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
    @ResponseBody
    public Result check_phone_id(@RequestParam("phone_id") String phone_id) {
        if (teacherService.phone_valid(phone_id)) {
            //TODO 向手机发送短信验证码
            return Result.success();
        } else {
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
    }

    @PostMapping("/addTeacher")
    @ApiOperation(value = "验证码验证成功后在教师表中添加一项")
    @ResponseBody
    public Result add_teacher(@RequestParam("phone_id") String phone_id, @RequestParam("password") String password) throws UserHasExistedException {
        teacherService.addUser(phone_id,password);
        logger.info("添加用户成功");
        return Result.success();
    }

    @PostMapping("/deleteTeacherById")
    @ApiOperation(value = "删除教师")
    @ResponseBody
    public Result delete_teacher(@RequestParam("user_id") int user_id) throws UserNotFoundException {
        teacherService.deleteTeacherById(user_id);
        logger.info("删除用户：id="+ user_id);
        return Result.success();
    }
}