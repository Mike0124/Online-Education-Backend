package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.StudentDto;
import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.StudentLikeCourse;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
	Student getStudentById(Integer userId);
	
	List<Student> getAllStudents();         //获取所有学生信息
	
	boolean phoneValid(String phoneId);       //判断手机号是否被注册
	
	void addUser(String phoneId, String password) throws ExistedException;      //添加刚注册的学生
	
	Student loginByPassword(String phoneId, String password) throws ParamErrorException, NotFoundException;
	
	void deleteStudentById(Integer userId);       //删除学生
	
	void completeStudent(Integer userId, StudentDto studentDto) throws NotFoundException;       //完善学生信息
	
	//学生评论课程
	void commentCourseByCourseId(CourseCommentDto courseCommentDto) throws NotFoundException, TasteException;
	
	CourseComment getCourseCommentByStudentAndCourse(Integer userId, Integer courseId) throws NotFoundException;
	
	void collectPreference(Integer userId, Integer[] prefersId);
	
	List<StudentPreference> getAllPreferences(Integer userId);
	
	String studentVip(Integer studentId, Integer type) throws NotFoundException, ParamErrorException;
	
	void likeCourse(Integer studentId, Integer courseId) throws NotFoundException;
	
	void cancelLikeCourse(Integer studentId, Integer courseId) throws NotFoundException;
	
	StudentLikeCourse getLikeByStudentAndCourse(Integer studentId, Integer courseId) throws NotFoundException;
	
	Page<StudentLikeCourse> getStudentLikeCourse(Pageable pageable, Integer studentId) throws NotFoundException;
	
	String getStudentItemCfResult(Integer studentId) throws NotFoundException, TasteException;
}