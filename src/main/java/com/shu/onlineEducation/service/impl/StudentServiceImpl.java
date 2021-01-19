package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.*;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePK;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	private PreferJpaRepository preferJpaRepository;
	
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
	public void deleteStudentById(int userId) throws NotFoundException {
		if (!studentJpaRepository.existsByUserId(userId)) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		studentJpaRepository.deleteStudentByUserId(userId);
	}
	
	@Override
	public Student loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException {
		if (!studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (!password.equals(studentJpaRepository.findStudentByPhoneId(phoneId).getPassword())) {
			throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
		}
		return studentJpaRepository.findStudentByPhoneId(phoneId);
	}
	
	@Override
	public void completeStudent(int userId, String nickname, String sex, String school, int majorId, int grade) throws NotFoundException {
		Student stu = studentJpaRepository.findStudentByUserId(userId);
		if (stu == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		Major major = majorJpaRepository.findMajorByMajorId(majorId);
		stu.setNickName(nickname);
		stu.setSex(sex);
		stu.setSchool(school);
		stu.setMajor(major);
		stu.setMajorId(major.getMajorId());
		stu.setGrade(grade);
		studentJpaRepository.save(stu);
	}
	
	
	@Override
	public void commentCourseByCourseId(String comment, int commentMark, int courseId, int studentId) throws NotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		CourseComment courseComment = new CourseComment();
		courseComment.setCommentMark(commentMark);
		courseComment.setContent(comment);
		courseComment.setLikes(0);
		courseComment.setTime(new Timestamp(System.currentTimeMillis()));
		courseComment.setCourse(courseJpaRepository.findCourseByCourseId(courseId));
		courseComment.setStudent(studentJpaRepository.findStudentByUserId(studentId));
		courseCommentJpaRepository.save(courseComment);
		Course course = courseJpaRepository.findCourseByCourseId(courseId);
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(courseId));
		courseJpaRepository.save(course);
	}
	
	@Override
	public void collectPreference(int userId, int[] prefersId) {
		studentPreferenceRepository.deleteAll();
		for (int prefer : prefersId) {
			StudentPreferencePK key = new StudentPreferencePK();
			key.setStudentId(userId);
			key.setPreferId(prefer);
			StudentPreference studentPreference = new StudentPreference();
			studentPreference.setStudentPreferencePK(key);
			studentPreferenceRepository.save(studentPreference);
		}
	}
	
	@Override
	public List<Integer> findAllPreferences(int userId) {
		List<Integer> preferList = new ArrayList<>();
		for (StudentPreference studentPreference:studentPreferenceRepository.findAllByStudentId(userId)) {
			preferList.add(studentPreference.getStudentPreferencePK().getPreferId());
		}
		return preferList;
	}
	
	
}
