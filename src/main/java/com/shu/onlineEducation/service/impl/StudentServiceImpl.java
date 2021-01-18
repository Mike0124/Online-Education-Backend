package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.entity.EmbeddedId.StudentCourseEnroll;
import com.shu.onlineEducation.entity.EmbeddedId.StudentCourseEnrollPrimaryKey;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePrimaryKey;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.moudle.request.CourseCommentRequest;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private StudentCourseJpaRepository studentCourseJpaRepository;
	@Autowired
	private StudentPreferenceRepository studentPreferenceRepository;
	@Autowired
	private CourseCommentJpaRepository courseCommentJpaRepository;
	
	@Override
	public List<Student> getAllStudents() {
		return studentJpaRepository.findAll();
	}
	
	@Override
	public boolean phoneValid(String phoneId) {
		return studentJpaRepository.existsByPhoneId(phoneId);
	}
	
	@Override
	public void addUser(String phoneId, String password) throws UserHasExistedException {
		Student student = new Student();
		student.setPhoneId(phoneId);
		student.setPassword(password);
		if (studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new UserHasExistedException();
		}
		studentJpaRepository.save(student);
	}
	
	@Override
	public void deleteStudentById(int userId) throws UserNotFoundException {
		if (!studentJpaRepository.existsByUserId(userId)) {
			throw new UserNotFoundException();
		}
		studentJpaRepository.deleteStudentByUserId(userId);
	}
	
	@Override
	public void completeStudent(int userId, String nickname, String sex, String school, int majorId, int grade) throws UserNotFoundException {
		Student stu = studentJpaRepository.findStudentByUserId(userId);
		if (stu == null) {
			throw new UserNotFoundException();
		}
		stu.setNickName(nickname);
		stu.setSex(sex);
		stu.setSchool(school);
		stu.setMajorId(majorId);
		stu.setGrade(grade);
		studentJpaRepository.save(stu);
	}
	
	@Override
	public void enrollCourseById(int userId, int courseId) throws CourseHasEnrolledException {
		StudentCourseEnrollPrimaryKey key = new StudentCourseEnrollPrimaryKey();
		key.setStudentId(userId);
		key.setCourseId(courseId);
		StudentCourseEnroll studentCourseEnroll = new StudentCourseEnroll();
		studentCourseEnroll.setStudentCourseEnrollPrimaryKey(key);
		if (studentCourseJpaRepository.existsByStudentCourseEnrollPrimaryKey(key)) {
			throw new CourseHasEnrolledException();
		} else {
			studentCourseJpaRepository.save(studentCourseEnroll);
		}
	}

	@Override
	public void commentCourseByCourseId(String comment, int commentMark, int courseId, int studentId) throws CourseNotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)){
			throw new CourseNotFoundException();
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
		log.info(String.valueOf(courseCommentJpaRepository.getCommentMarkAvg(courseId)));
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(courseId));
		courseJpaRepository.save(course);
	}

	@Override
	public void collectPreference(int userId, int[] prefersId) {
		//检查该学生是否已存入偏好
		if (studentPreferenceRepository.findAllByStudentId(userId).isEmpty()) {
			for (int prefer : prefersId) {
				StudentPreferencePrimaryKey key = new StudentPreferencePrimaryKey();
				key.setStudentId(userId);
				key.setPreferId(prefer);
				StudentPreference studentPreference = new StudentPreference();
				studentPreference.setStudentPreferencePrimaryKey(key);
				studentPreferenceRepository.save(studentPreference);
			}
		}
	}
	
	@Override
	public Student loginByPassword(String phoneId, String password) throws UserNotFoundException, PassWordErrorException {
		if (!studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new UserNotFoundException();
		}
		if (!password.equals(studentJpaRepository.findStudentByPhoneId(phoneId).getPassword())) {
			throw new PassWordErrorException();
		}
		return studentJpaRepository.findStudentByPhoneId(phoneId);
	}
}
