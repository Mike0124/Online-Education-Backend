package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

	boolean existsByCourseId(int courseId);

	boolean existsByPreferId(int preferId);

	boolean existsByNeedVip(boolean needVip);
	
	Course findCourseByCourseId(int courseId);
	
	List<Course> findAllByPreferId(int preferId);

	List<Course> findAllByNeedVip(boolean needVip);

	List<Course> findAllByPreferId(Pageable pageable, int preferId);

	List<Course> findAllByNeedVip(Pageable pageable, boolean needVip);

	List<Course> findAllByNeedVipAndPreferId(boolean needVip, int preferId);

	List<Course> findAllByNeedVipAndPreferId(Pageable pageable, boolean needVip, int preferId);
	
	@Transactional
	void deleteByCourseId(int courseId);
}
