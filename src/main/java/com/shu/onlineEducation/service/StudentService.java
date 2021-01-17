package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.utils.ExceptionUtil.PassWordErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface StudentService {
	
	List<Student> getAllStudents();         //获取所有学生信息
	
	boolean phoneValid(String phoneId);       //判断手机号是否被注册
	
	void addUser(String phoneId, String password) throws UserHasExistedException;      //添加刚注册的学生
	
	void deleteStudentById(int userId) throws UserNotFoundException;        //删除学生
	
	void completeStudent(int userId, String nickname, String sex, String school, int majorId, int grade)
			throws UserNotFoundException;       //完善学生信息
	
	void collectPreference(int userId, int[] prefersId);

	Student loginByPassword(String phoneId, String password) throws UserNotFoundException, PassWordErrorException;
}