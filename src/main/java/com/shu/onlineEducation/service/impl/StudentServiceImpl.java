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
import org.springframework.stereotype.Service;

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
		Student student = new Student();
		student.setPhoneId(phoneId);
		student.setPassword(password);
		if (studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new ExistedException(ResultCode.USER_HAS_EXISTED);
		}
		studentJpaRepository.save(student);
	}
	
	@Override
	public void deleteStudentById(Integer userId) throws NotFoundException {
		if (!studentJpaRepository.existsByUserId(userId)) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
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
		return studentJpaRepository.findByPhoneId(phoneId);
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
		Course course = courseJpaRepository.findCourseByCourseId(courseCommentDto.getCourseId());
		Student student = studentJpaRepository.findByUserId(courseCommentDto.getStudentId());
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		CourseComment courseComment = new CourseComment();
		courseComment.setCommentMark(courseCommentDto.getCommentMark());
		courseComment.setContent(courseCommentDto.getComment());
		courseComment.setLikes(0);
		courseComment.setTime(DateUtil.getNowTimeStamp());
		courseComment.setCourseId(course.getCourseId());
		courseComment.setStudentId(student.getUserId());
		courseCommentJpaRepository.save(courseComment);
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(course.getCourseId()));
		courseJpaRepository.save(course);
		if (courseComment.getCommentId() % 20 == 0) {
			courseCommentService.trainLatest(courseComment.getCommentId());
		}
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
}
