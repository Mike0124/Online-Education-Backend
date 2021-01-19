package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
	List<Course> getAllCourses();        //获取所有课程信息
	
	List<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) throws NotFoundException;     //获取某子专业的所有课程

	List<Course> getAllCoursesByNeedVip(boolean needVip) throws NotFoundException;

	List<Course> getAllCoursesByNeedVipAndPreferId(boolean needVip, int preferId) throws NotFoundException;

	void updateCourseStatusById(int courseId, int status);    //更新课程状态
	
	void deleteCourseById(int courseId);
}
