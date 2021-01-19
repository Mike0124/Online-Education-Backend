package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.utils.ExceptionUtil.*;

import java.util.List;

public interface StudentService {
	
	List<Student> getAllStudents();         //获取所有学生信息
	
	boolean phoneValid(String phoneId);       //判断手机号是否被注册
	
	void addUser(String phoneId, String password) throws ExistedException;      //添加刚注册的学生
	
	Student loginByPassword(String phoneId, String password) throws ParamErrorException, NotFoundException;
	
	void deleteStudentById(int userId) throws NotFoundException;        //删除学生
	
	void completeStudent(int userId, String nickname, String sex, String school, int majorId, int grade)
			throws NotFoundException;       //完善学生信息

	//学生评论课程
	void commentCourseByCourseId(String comment, int commentMark, int courseId, int studentId) throws NotFoundException;
	
	void collectPreference(int userId, int[] prefersId);
	
	List<Integer> findAllPreferences(int userId);
}