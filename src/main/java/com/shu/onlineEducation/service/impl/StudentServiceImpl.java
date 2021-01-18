package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.*;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePK;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
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
	public Student loginByPassword(String phoneId, String password) throws UserNotFoundException, PassWordErrorException {
		if (!studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new UserNotFoundException();
		}
		if (!password.equals(studentJpaRepository.findStudentByPhoneId(phoneId).getPassword())) {
			throw new PassWordErrorException();
		}
		return studentJpaRepository.findStudentByPhoneId(phoneId);
	}
	
	@Override
	public void completeStudent(int userId, String nickname, String sex, String school, int majorId, int grade) throws UserNotFoundException {
		Student stu = studentJpaRepository.findStudentByUserId(userId);
		if (stu == null) {
			throw new UserNotFoundException();
		}
		//TODO:合并各种Exception
		Major major = majorJpaRepository.findMajorByMajorId(majorId);
//		if (major == null){
//			throw new MajorNotFoundException();
//		}
		stu.setNickName(nickname);
		stu.setSex(sex);
		stu.setSchool(school);
		stu.setMajor(major);
		stu.setGrade(grade);
		studentJpaRepository.save(stu);
	}
	
	
	@Override
	public void commentCourseByCourseId(String comment, int commentMark, int courseId, int studentId) throws CourseNotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
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
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(courseId));
		courseJpaRepository.save(course);
	}
	
	@Override
	public void collectPreference(int userId, int[] prefersId) {
		for (int prefer : prefersId) {
			StudentPreferencePK key = new StudentPreferencePK();
			key.setStudent(studentJpaRepository.findStudentByUserId(userId));
			key.setPrefer(preferJpaRepository.findPreferByPreferId(prefer));
			StudentPreference studentPreference = new StudentPreference();
			studentPreference.setStudentPreferencePK(key);
			studentPreferenceRepository.save(studentPreference);
		}
	}
	
	@Override
	public List<Prefer> findAllPreferences(int userId) {
		List<Prefer> preferList = new ArrayList<>();
		for (StudentPreference studentPreference:studentPreferenceRepository.findAllByStudentId(userId)) {
			preferList.add(studentPreference.getStudentPreferencePK().getPrefer());
		}
		return preferList;
	}
	
	
}
