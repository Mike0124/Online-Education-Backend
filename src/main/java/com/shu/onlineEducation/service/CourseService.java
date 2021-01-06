package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Course;

import java.util.List;

public interface CourseService {
	List<Course> getAllCourses();        //获取所有课程信息
	
	List<Course> getAllCoursesByPreferId(int preferId);     //获取某子专业的所有课程
	
	void updateCourseStatusById(int courseId, int status);    //更新课程状态
	
	void deleteCourseById(int courseId);
}
