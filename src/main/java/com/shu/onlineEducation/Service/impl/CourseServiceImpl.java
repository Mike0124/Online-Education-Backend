package com.shu.onlineEducation.Service.impl;

import com.shu.onlineEducation.Dao.CourseJpaRepository;
import com.shu.onlineEducation.Entity.Course;
import com.shu.onlineEducation.Service.CourseService;
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
}
