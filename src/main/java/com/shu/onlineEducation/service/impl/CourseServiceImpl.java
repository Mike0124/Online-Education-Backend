package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Course> getAllCoursesByPreferId(int preferId) {
        return courseJpaRepository.findAllByPreferId(preferId);
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
