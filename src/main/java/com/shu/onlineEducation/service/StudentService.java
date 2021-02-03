package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.StudentDto;
import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.utils.ExceptionUtil.*;

import java.util.List;

public interface StudentService {
	Student getStudentById(Integer userId);
	
	List<Student> getAllStudents();         //获取所有学生信息
	
	boolean phoneValid(String phoneId);       //判断手机号是否被注册
	
	void addUser(String phoneId, String password) throws ExistedException;      //添加刚注册的学生
	
	Student loginByPassword(String phoneId, String password) throws ParamErrorException, NotFoundException;
	
	void deleteStudentById(Integer userId) throws NotFoundException;        //删除学生
	
	void completeStudent(Integer userId, StudentDto studentDto)throws NotFoundException;       //完善学生信息
	
	//学生评论课程
	void commentCourseByCourseId(CourseCommentDto courseCommentDto) throws NotFoundException;
	
	void collectPreference(Integer userId, Integer[] prefersId);
	
	List<StudentPreference> getAllPreferences(Integer userId);
}