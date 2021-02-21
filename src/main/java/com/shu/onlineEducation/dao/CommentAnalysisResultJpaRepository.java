package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.CommentAnalysisResult;
import com.shu.onlineEducation.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAnalysisResultJpaRepository extends JpaRepository<CommentAnalysisResult,Integer> {
	CommentAnalysisResult findByCourse(Course course);
}
