package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	
	@Override
	public List<Course> getAllCourses() {
		return courseJpaRepository.findAll();
	}
	
	@Override
	public List<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) throws NotFoundException {
		if (courseJpaRepository.existsByPreferId(preferId)) {
			return courseJpaRepository.findAllByPreferId(pageable, preferId);
		} else {
			throw new NotFoundException(3002,"此偏好课程不存在");
		}
	}
	
	@Override
	public List<Course> getAllCoursesByNeedVip(boolean needVip) throws NotFoundException {
		if (courseJpaRepository.existsByNeedVip(needVip)) {
			return courseJpaRepository.findAllByNeedVip(needVip);
		} else {
			throw new NotFoundException(3003,"VIP课程不存在");
		}
	}
	
	@Override
	public List<Course> getAllCoursesByNeedVipAndPreferId(boolean needVip, int preferId) throws
			NotFoundException {
		if (!courseJpaRepository.existsByNeedVip(needVip)) {
			throw new NotFoundException(3003,"此偏好VIP课程不存在");
		}
		return courseJpaRepository.findAllByNeedVipAndPreferId(needVip, preferId);
	}
	
	@Override
	public synchronized void updateCourseStatusById(int courseId, int status) {
		Course course = courseJpaRepository.findCourseByCourseId(courseId);
		course.setStatus(status);
		courseJpaRepository.save(course);
	}
	
	@Override
	public void deleteCourseById(int courseId) {
		if (courseJpaRepository.existsByCourseId(courseId)) {
			courseJpaRepository.deleteByCourseId(courseId);
		}
	}
}
