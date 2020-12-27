package com.shu.onlineEducation.Service.impl;

import com.shu.onlineEducation.Dao.CourseJpaRepository;
import com.shu.onlineEducation.Dao.StudentCourseJpaRepository;
import com.shu.onlineEducation.Dao.StudentJpaRepository;
import com.shu.onlineEducation.Dao.StudentPreferenceRepository;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentCourseEnroll;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentCourseEnrollPrimaryKey;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentPreferencePrimaryKey;
import com.shu.onlineEducation.Entity.Student;
import com.shu.onlineEducation.Service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.CourseHasEnrolledException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		studentJpaRepository.deleteStudentInfoByUserId(userId);
	}
	
	@Override
	public void completeStudentInfo(int userId, String nickname, String sex, String school, int majorId, int grade) throws UserNotFoundException {
		Student stu = studentJpaRepository.findStudentInfoByUserId(userId);
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
	public void collectPreference(int userId, int[] prefersId) {
		if (studentPreferenceRepository.findAllByStudentId(userId).isEmpty()) {//检查该学生是否已存入偏好
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
}
