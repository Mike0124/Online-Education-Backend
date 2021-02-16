package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
	
	boolean existsByCourseId(int courseId);
	
	Course findByCourseId(int courseId);
	
	@Query(value = "select * from course where prefer_id = :preferId and course_id != :courseId order by course_watches desc limit 5", nativeQuery = true)
	List<Course> findRelatedCourses(@Param(value = "courseId") Integer courseId, @Param(value = "preferId") Integer preferId);
	
	@Query(value = "select course_id, name,intro, upload_time, teacher_id, course.prefer_id , course_pic, need_vip, course_status, course_watches, course_avg_mark from course, prefer p where p.major_id = :majorId", nativeQuery = true)
	Page<Course> findByMajor(Pageable pageable, @Param("majorId") Integer majorId);
	
	@Query(value = "select * from course where name regexp :regex", nativeQuery = true)
	Page<Course> findWithRegex(Pageable pageable, @Param("regex") String regex);
	
	Page<Course> findAllByPreferIdAndStatus(Pageable pageable, int preferId, int status);
	
	Page<Course> findAllByNeedVipAndPreferIdAndStatus(Pageable pageable, boolean needVip, int preferId, int status);
	
	Page<Course> findAllByTeacherIdAndStatus(Pageable pageable, int teacherId, int status);
	
	@Transactional
	void deleteByCourseId(int courseId);
}
