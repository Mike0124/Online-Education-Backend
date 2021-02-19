package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.LoginDto;
import com.shu.onlineEducation.common.dto.RegisterDto;
import com.shu.onlineEducation.common.dto.StudentDto;
import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.common.dto.course.WatchRecordDto;
import com.shu.onlineEducation.service.LikeService;
import com.shu.onlineEducation.service.WatchRecordService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.JwtUtil;
import com.shu.onlineEducation.utils.MapUtil;
import com.shu.onlineEducation.utils.RedisUtil;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/Student")
@Api(tags = "1-学生模块")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private SmsController smsController;
	@Autowired
	private WatchRecordService watchRecordService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisUtil redisUtil;
	
	@PostMapping("/getStudentById")
	@ApiOperation(value = "获取当前学生信息")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_ADMIN')")
	@ResponseBody
	public Result findById(@RequestParam("user_id") int userId, @RequestHeader("Authorization") String jwt) {
		return Result.success(studentService.getStudentById(userId));
	}
	
	@GetMapping("/getStudent")
	@ApiOperation(value = "获取所有用户详情")
//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Iterable<Student> findAll() {
		return studentService.getAllStudents();
	}
	
	@PostMapping("/checkByPhoneId")
	@ApiOperation(value = "验证手机号是否被注册，未被注册则发送验证码")
	@ResponseBody
	public Result checkPhoneId(@RequestParam("phone_id") String phoneId) {
		if (!studentService.phoneValid(phoneId)) {
			smsController.sendCode(phoneId);
			return Result.success();
		} else {
			return Result.failure(ResultCode.USER_HAS_EXISTED);
		}
	}
	
	@PostMapping("/addStudent")
	@ApiOperation(value = "验证成功后添加学生")
	@ResponseBody
	public Result add(@RequestBody RegisterDto registerDto) throws ExistedException {
		if (registerDto.getCode().equals(redisUtil.get(registerDto.getPhone()))) {
			studentService.addUser(registerDto.getPhone(), registerDto.getPassword());
			log.info("添加用户成功");
			return Result.success();
		} else {
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
		
	}
	
	@PostMapping("/loginByPassword")
	@ApiOperation(value = "登录")
	@ResponseBody
	public Result loginByPassword(@RequestBody LoginDto loginDto, HttpServletResponse response) throws NotFoundException, ParamErrorException {
		Student student = studentService.loginByPassword(loginDto.getPhone(), loginDto.getPassword());
		String jwt = jwtUtil.generateToken(student.getPhoneId(), student.getPassword(), "student");
		response.setHeader("Authorization", jwt);
		response.setHeader("Access-control-Expose-Headers", "Authorization");
		log.info("登录成功");
		return Result.success(student);
	}
	
	@PostMapping("/deleteStudentById")
	@ApiImplicitParam(name = "user_id", value = "用户标识", required = true, paramType = "form", dataType = "String")
	@ApiOperation(value = "删除学生")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result delete(@RequestParam("user_id") Integer userId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		studentService.deleteStudentById(userId);
		log.info("删除用户：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/commentCourseByCourseId")
	@ApiOperation(value = "评价课程")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result comment(@RequestBody CourseCommentDto courseCommentDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		studentService.commentCourseByCourseId(courseCommentDto);
		return Result.success();
	}
	
	@PostMapping("/getCourseCommentByStudentAndCourse")
	@ApiOperation(value = "获取当前学生对该课程的评价")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getCourseCommentByStudentAndCourse(@RequestParam("user_id") Integer userId, @RequestParam("course_id") Integer courseId) throws NotFoundException {
		return Result.success(studentService.getCourseCommentByStudentAndCourse(userId, courseId));
	}
	
	@PostMapping("/completeStudentById")
	@ApiOperation(value = "完善学生信息")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result complete(@RequestParam("user_id") Integer userId, @RequestBody StudentDto studentDto, @RequestHeader("Authorization") String jwt)
			throws NotFoundException {
		studentService.completeStudent(userId, studentDto);
		log.info("完善学生信息：id=" + userId);
		return Result.success();
	}
	
	@PostMapping("/collectPreferences")
	@ApiOperation(value = "收集学生偏好")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result collectPreference(@RequestParam("user_id") Integer userId, @RequestParam("prefers") Integer[] prefersId, @RequestHeader("Authorization") String jwt) {
		studentService.collectPreference(userId, prefersId);
		return Result.success();
	}
	
	@PostMapping("/findAllPreferences")
	@ApiOperation(value = "获取所有当前学生的偏好")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result findAllPreferences(@RequestParam("user_id") Integer userId, @RequestHeader("Authorization") String jwt) {
		log.info("test:" + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return Result.success(studentService.getAllPreferences(userId));
	}
	
	@PostMapping("/findAllWatchRecords")
	@ApiOperation(value = "获取当前学生的观看记录")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result findAllWatchRecords(Integer page, @RequestParam("user_id") Integer userId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "watchTime"));
		return Result.success(MapUtil.pageResponse(watchRecordService.getAllByStudent(pageable, userId)));
	}
	
	@PostMapping("/getWatchRecordByStudentAndCourse")
	@ApiOperation(value = "获取当前学生该课程的观看记录")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getWatchRecordByStudentAndCourse(@RequestParam("user_id") Integer userId, Integer courseId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(watchRecordService.getByStudentAndCourse(userId, courseId));
	}
	
	@PostMapping("/addWatchRecords")
	@ApiOperation(value = "添加、修改观看记录")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addWatchRecords(@RequestBody WatchRecordDto watchRecordDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		watchRecordService.addWatchRecords(watchRecordDto);
		return Result.success();
	}
	
	@PostMapping("/deleteWatchRecords")
	@ApiOperation(value = "删除观看记录")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteWatchRecords(Integer watchRecordId, @RequestHeader("Authorization") String jwt) {
		watchRecordService.deleteWatchRecord(watchRecordId);
		return Result.success();
	}
	
	@PostMapping("/getLikeCommentByStudent")
	@ApiOperation(value = "获取当前学生的点赞评论")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getLikeCommentByStudent(Integer page, @RequestParam("user_id") Integer userId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "time"));
		return Result.success(MapUtil.pageResponse(likeService.getByStudent(pageable, userId)));
	}
	
	@PostMapping("/likeComment")
	@ApiOperation(value = "点赞评论")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result likeComment(@RequestParam("user_id") Integer userId, Integer commentId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		likeService.addByStudentAndCourseComment(userId, commentId);
		return Result.success();
	}
	
	@PostMapping("/dislikeComment")
	@ApiOperation(value = "取消点赞评论")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result dislikeComment(@RequestParam("user_id") Integer userId, Integer commentId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		likeService.deleteByStudentAndCourseComment(userId, commentId);
		return Result.success();
	}
	
	@PostMapping("/getStudentIsLikedComments")
	@ApiOperation(value = "获取当前学生是否点赞了这些评论")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getStudentIsLikedComments(@RequestParam("user_id") Integer userId, @RequestBody Integer[] commentIds, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		return Result.success(likeService.getByStudentAndComments(userId, Arrays.asList(commentIds)));
	}
	
	@PostMapping("/studentVip")
	@ApiOperation(value = "授予学生Vip权限 1.一个月	 2.半年  3.一年")
	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@ResponseBody
	public Result studentVip(@RequestParam("user_id") Integer userId, Integer type, @RequestHeader("Authorization") String jwt) throws NotFoundException, ParamErrorException {
		return Result.success(studentService.studentVip(userId, type));
	}
}
