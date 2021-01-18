package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.CoursePreferNotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.CourseVipNotFoundException;
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
	public List<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) throws CoursePreferNotFoundException {
		if (courseJpaRepository.existsByPreferId(preferId)) {
			return courseJpaRepository.findAllByPreferId(pageable, preferId);
		} else {
			throw new CoursePreferNotFoundException();
		}
	}
	
	@Override
	public List<Course> getAllCoursesByNeedVip(boolean needVip) throws CourseVipNotFoundException {
		if (courseJpaRepository.existsByNeedVip(needVip)) {
			return courseJpaRepository.findAllByNeedVip(needVip);
			
		} else {
			throw new CourseVipNotFoundException();
		}
	}
	
	@Override
	public List<Course> getAllCoursesByNeedVipAndPreferId(boolean needVip, int preferId) throws
			CoursePreferNotFoundException, CourseVipNotFoundException {
		if (!courseJpaRepository.existsByNeedVip(needVip)) {
			throw new CourseVipNotFoundException();
		}
		if (!courseJpaRepository.existsByPreferId(preferId)) {
			throw new CoursePreferNotFoundException();
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
