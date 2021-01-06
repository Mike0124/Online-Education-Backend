package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
	boolean existsByCourseId(int courseId);
	
	Course findCourseByCourseId(int courseId);
	
	List<Course> findAllByPreferId(int preferId);
	
	@Transactional
	void deleteByCourseId(int courseId);
}