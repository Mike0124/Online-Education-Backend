package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.StudentDto;
import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.*;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePK;
import com.shu.onlineEducation.service.CourseCommentService;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private StudentPreferenceRepository studentPreferenceRepository;
	@Autowired
	private CourseCommentJpaRepository courseCommentJpaRepository;
	@Autowired
	private MajorJpaRepository majorJpaRepository;
	@Autowired
	private StudentLikeCourseJpaRepository likeCourseJpaRepository;
	@Autowired
	private CourseCommentService courseCommentService;
	
	@Override
	public Student getStudentById(Integer userId) {
		return studentJpaRepository.findByUserId(userId);
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentJpaRepository.findAll();
	}
	
	@Override
	public boolean phoneValid(String phoneId) {
		return studentJpaRepository.existsByPhoneId(phoneId);
	}
	
	@Override
	public void addUser(String phoneId, String password) throws ExistedException {
		if (studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new ExistedException(ResultCode.USER_HAS_EXISTED);
		}
		Student student = new Student();
		student.setPhoneId(phoneId);
		student.setPassword(password);
		studentJpaRepository.save(student);
	}
	
	@Override
	public void deleteStudentById(Integer userId) {
		studentJpaRepository.deleteStudentByUserId(userId);
	}
	
	@Override
	public Student loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException {
		Student student = studentJpaRepository.findByPhoneId(phoneId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (!password.equals(student.getPassword())) {
			throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
		}
		return student;
	}
	
	@Override
	public void completeStudent(Integer userId, StudentDto studentDto) throws NotFoundException {
		Student stu = studentJpaRepository.findByUserId(userId);
		Major major = majorJpaRepository.findMajorByMajorId(studentDto.getMajorId());
		if (stu == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (major == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		stu.setNickName(studentDto.getNickname());
		stu.setSex(studentDto.getSex());
		stu.setSchool(studentDto.getSchool());
		stu.setMajorId(major.getMajorId());
		stu.setGrade(studentDto.getGrade());
		stu.setStudentPicUrl(studentDto.getPicUrl());
		studentJpaRepository.save(stu);
	}
	
	
	@Override
	public void commentCourseByCourseId(CourseCommentDto courseCommentDto) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseCommentDto.getCourseId());
		Student student = studentJpaRepository.findByUserId(courseCommentDto.getStudentId());
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		//每个学生只能评论一个课程一次
		CourseComment courseComment = courseCommentJpaRepository.findByCourseAndStudent(course, student);
		if (courseComment == null) {
			courseComment = new CourseComment();
			courseComment.setCourseId(course.getCourseId());
			courseComment.setStudentId(student.getUserId());
		}
		courseComment.setCommentMark(courseCommentDto.getCommentMark());
		courseComment.setContent(courseCommentDto.getComment());
		courseComment.setLikes(0);
		courseCommentJpaRepository.save(courseComment);
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(course.getCourseId()));
		courseJpaRepository.save(course);
		courseCommentService.analysisByCourse(courseCommentDto.getCourseId());
		//每20条评论训练一次情感分析模型
		if (courseComment.getCommentId() % 20 == 0) {
			courseCommentService.trainLatest(courseComment.getCommentId());
		}
	}
	
	@Override
	public CourseComment getCourseCommentByStudentAndCourse(Integer userId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(userId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return courseCommentJpaRepository.findByCourseAndStudent(course, student);
	}
	
	@Override
	public void collectPreference(Integer userId, Integer[] prefersId) {
		studentPreferenceRepository.deleteAllByStudentId(userId);
		for (Integer prefer : prefersId) {
			StudentPreferencePK key = new StudentPreferencePK();
			key.setStudentId(userId);
			key.setPreferId(prefer);
			StudentPreference studentPreference = new StudentPreference();
			studentPreference.setStudentPreferencePK(key);
			studentPreferenceRepository.save(studentPreference);
		}
	}
	
	@Override
	public List<StudentPreference> getAllPreferences(Integer userId) {
		return studentPreferenceRepository.findAllByStudentId(userId);
	}
	
	@Override
	public String studentVip(Integer studentId, Integer type) throws NotFoundException, ParamErrorException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		Date vipDate = DateUtil.stringToDate(student.getVipDate());
		Date nowDate = DateUtil.getNowDate();
		vipDate = nowDate.before(vipDate) ? vipDate : nowDate;
		switch (type) {
			case (1):
				vipDate = DateUtil.getDateAddMonth(vipDate, 1);
				break;
			case (2):
				vipDate = DateUtil.getDateAddMonth(vipDate, 6);
				break;
			case (3):
				vipDate = DateUtil.getDateAddYear(vipDate, 1);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + type);
		}
		student.setVipDate(DateUtil.dateToTimestamp(vipDate));
		studentJpaRepository.saveAndFlush(student);
		return new SimpleDateFormat("yyyy-MM-dd").format(vipDate);
	}
	
	@Override
	public void likeCourse(Integer studentId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		StudentLikeCourse studentLikeCourse = likeCourseJpaRepository.findByStudentAndCourse(student, course);
		if (studentLikeCourse != null) {
			return;
		} else {
			studentLikeCourse = new StudentLikeCourse();
			studentLikeCourse.setStudentId(student.getUserId());
			studentLikeCourse.setCourseId(course.getCourseId());
			likeCourseJpaRepository.saveAndFlush(studentLikeCourse);
		}
	}
	
	@Override
	public void cancelLikeCourse(Integer studentId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		likeCourseJpaRepository.deleteByStudentIdAndCourseId(student.getUserId(), course.getCourseId());
	}
	
	@Override
	public Page<StudentLikeCourse> getStudentLikeCourse(Pageable pageable, Integer studentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return likeCourseJpaRepository.findByStudent(student, pageable);
	}
}
