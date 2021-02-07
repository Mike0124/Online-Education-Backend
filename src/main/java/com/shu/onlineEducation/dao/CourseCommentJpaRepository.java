package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.CourseComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CourseCommentJpaRepository extends JpaRepository<CourseComment, Integer> {
	@Query(value = "select avg(comment_mark) from course_comment where course_id =:courseId", nativeQuery = true)
	BigDecimal getCommentMarkAvg(@Param("courseId") int courseId);
	
	@Query(value = "select * from course_comment where comment_id between (:start) and (:end)", nativeQuery = true)
	List<CourseComment> findByBetween(@Param("start") Integer start, @Param("end") Integer end);
	
	List<CourseComment> findByCourse(Pageable pageable, Course course);
	
	List<CourseComment> findByCourse(Course course);
}
