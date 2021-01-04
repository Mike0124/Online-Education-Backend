package com.shu.onlineEducation.Controller;

import com.shu.onlineEducation.Entity.Teacher;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.Service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;
import com.shu.onlineEducation.utils.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Teacher")
@Api(tags = "教师模块")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeacher")
    @ApiOperation(value = "获取所有用户详情")
    @ResponseBody
    public Result showInfo() {
        return Result.success(teacherService.getAllTeachers());
    }

    @PostMapping("/checkByPhoneId")
    @ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String")
    @ApiOperation(value = "验证手机号是否被注册，没被注册则发送验证码")
    @ResponseBody
    public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
        if (!teacherService.phoneValid(phoneId)) {
            //TODO 向手机发送短信验证码
            return Result.success();
        } else {
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }
    }

    @PostMapping("/addTeacher")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
//    })
    @ApiOperation(value = "验证码验证成功后在教师表中添加一项")
    @ResponseBody
    public Result add(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password) throws UserHasExistedException {
        teacherService.addUser(phoneId,password);
        logger.info("添加用户成功");
        return Result.success();
    }
    
    @PostMapping("/loginByPassword")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "phone_id", value = "手机号", required = true, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
//    })
    @ApiOperation(value = "用户登录")
    @ResponseBody
    public Result loginByPassword(@RequestParam("phone_id") String phoneId, @RequestParam("password") String password) throws Exception {
        Teacher teacher = teacherService.loginByPassword(phoneId,password);
        logger.info("登录成功");
        return Result.success(teacher);
    }
    

    @PostMapping("/deleteTeacherById")
    @ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
    @ApiOperation(value = "删除教师")
    @ResponseBody
    public Result delete(@RequestParam("user_id") int userId) throws UserNotFoundException {
        teacherService.deleteTeacherById(userId);
        logger.info("删除用户：id="+ userId);
        return Result.success();
    }

    @PostMapping("/completeTeacherById")
    @ApiOperation(value = "完善教师信息")
    @ResponseBody
    public Result complete(@RequestParam("user_id") int userId,@RequestParam("nickname") String nickname,
                           @RequestParam("sex") String sex, @RequestParam("school") String school,
                           @RequestParam("major") String major)throws UserNotFoundException{
        teacherService.completeTeacherInfo(userId,nickname,sex,school,major);
        logger.info("完善教师信息：id=" + userId);
        return Result.success();
    }
    
}