package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
	
	boolean existsByCourseId(int courseId);
	
	Course findCourseByCourseId(int courseId);
	
	Page<Course> findAllByPreferIdAndStatus(Pageable pageable, int preferId, int status);
	
	Page<Course> findAllByNeedVipAndPreferIdAndStatus(Pageable pageable, boolean needVip, int preferId, int status);
	
	Page<Course> findAllByTeacherIdAndStatus(Pageable pageable, int teacherId, int status);
	
	@Transactional
	void deleteByCourseId(int courseId);
}
