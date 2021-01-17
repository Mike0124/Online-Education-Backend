package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.dao.StudentPreferenceRepository;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePrimaryKey;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.ExceptionUtil.PassWordErrorException;
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
