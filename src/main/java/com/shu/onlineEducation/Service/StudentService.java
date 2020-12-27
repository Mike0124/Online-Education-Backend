package com.shu.onlineEducation.Service;

import com.shu.onlineEducation.Entity.Student;
import com.shu.onlineEducation.utils.ExceptionUtil.CourseHasEnrolledException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface StudentService {
	
	List<Student> getAllStudents();         //获取所有学生信息
	
	boolean phoneValid(String phone_id);       //判断手机号是否被注册
	
	void addUser(String phone_id, String password) throws UserHasExistedException;      //添加刚注册的学生
	
	void deleteStudentById(int userId) throws UserNotFoundException;        //删除学生
	
	void completeStudentInfo(int userId, String nickname, String sex, String school, int majorId, int grade)
			throws UserNotFoundException;       //完善学生信息
	
	//学生报名课程
	void enrollCourseById(int userId, int courseId) throws CourseHasEnrolledException;
	
	void collectPreference(int userId, int[] prefersId);
}