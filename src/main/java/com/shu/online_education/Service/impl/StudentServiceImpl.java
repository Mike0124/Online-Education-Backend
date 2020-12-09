package com.shu.online_education.Service.impl;

import com.shu.online_education.Dao.ClassJpaRepository;
import com.shu.online_education.Dao.StudentClassJpaRepository;
import com.shu.online_education.Dao.StudentJpaRepository;
import com.shu.online_education.Dao.StudentPreferenceRepository;
import com.shu.online_education.Entity.EmbeddedId.StudentClassEnroll;
import com.shu.online_education.Entity.EmbeddedId.StudentClassEnrollPrimaryKey;
import com.shu.online_education.Entity.EmbeddedId.StudentPreference;
import com.shu.online_education.Entity.EmbeddedId.StudentPreferencePrimaryKey;
import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.Service.StudentService;
import com.shu.online_education.utils.ExceptionUtil.ClassHasEnrolledException;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private ClassJpaRepository classJpaRepository;
	@Autowired
	private StudentClassJpaRepository studentClassJpaRepository;
	@Autowired
	private StudentPreferenceRepository studentPreferenceRepository;
	
	@Override
	public List<StudentInfo> getAllStudents() {
		return studentJpaRepository.findAll();
	}
	
	@Override
	public boolean phone_valid(String phone_id) {
		return studentJpaRepository.existsByPhoneId(phone_id);
	}
	
	@Override
	public void addUser(String phone_id, String password) throws UserHasExistedException {
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setPhoneId(phone_id);
		studentInfo.setPassword(password);
		if (studentJpaRepository.existsByPhoneId(phone_id)) {
			throw new UserHasExistedException();
		}
		studentJpaRepository.save(studentInfo);
	}
	
	@Override
	public void deleteStudentById(int userId) throws UserNotFoundException {
		if (!studentJpaRepository.existsByUserId(userId)) {
			throw new UserNotFoundException();
		}
		studentJpaRepository.deleteStudentInfoByUserId(userId);
	}
	
	@Override
	public void completeStudentInfo(int userId, String nickname, String sex, String school, String major, int grade) throws UserNotFoundException {
		StudentInfo stu = studentJpaRepository.findStudentInfoByUserId(userId);
		if (stu == null) {
			throw new UserNotFoundException();
		}
		stu.setNickName(nickname);
		stu.setSex(sex);
		stu.setSchool(school);
		stu.setMajor(major);
		stu.setGrade(grade);
		studentJpaRepository.save(stu);
	}
	
	@Override
	public void enrollClassById(int userId, int classId) throws ClassHasEnrolledException {
		StudentClassEnrollPrimaryKey key = new StudentClassEnrollPrimaryKey();
		key.setStudent_id(userId);
		key.setClass_id(classId);
		StudentClassEnroll studentClassEnroll = new StudentClassEnroll();
		studentClassEnroll.setStudentClassEnrollPrimaryKey(key);
		if (studentClassJpaRepository.existsByStudentClassEnrollPrimaryKey(key)) {
			throw new ClassHasEnrolledException();
		} else {
			studentClassJpaRepository.save(studentClassEnroll);
		}
	}
	
	@Override
	public void collectPreference(int userId, int[] prefersId) {
		if (studentPreferenceRepository.findAllByStudentId(userId).isEmpty()) {//检查该学生是否已存入偏好
			for (int prefer : prefersId) {
				StudentPreferencePrimaryKey key = new StudentPreferencePrimaryKey();
				key.setStudent_id(userId);
				key.setPrefer_id(prefer);
				StudentPreference studentPreference = new StudentPreference();
				studentPreference.setStudentPreferencePrimaryKey(key);
				studentPreferenceRepository.save(studentPreference);
			}
		}
	}
}
