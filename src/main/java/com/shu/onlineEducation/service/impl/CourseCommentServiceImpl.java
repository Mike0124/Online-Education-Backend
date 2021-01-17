package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseCommentJpaRepository;
import com.shu.onlineEducation.service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {

    @Autowired
    CourseCommentJpaRepository courseCommentJpaRepository;

    @Override
    public BigDecimal getCommentMarkAvg(int courseId) {
        return courseCommentJpaRepository.getCommentMarkAvg(courseId);
    }
}
