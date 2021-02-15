package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.entity.Student;
import org.springframework.data.domain.Page;
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
	
	@Query(value = "select * from course_comment where course_id =:courseId and content regexp :regex", nativeQuery = true)
	Page<CourseComment> findByCourseWithRegex(Pageable pageable, @Param("regex") String regex, @Param("courseId") Integer courseId);
	
	Page<CourseComment> findByCourse(Pageable pageable, Course course);
	
	List<CourseComment> findByCourse(Course course);
	
	CourseComment findByCourseAndStudent(Course course, Student student);
	
	CourseComment findByCommentId(Integer commentId);
}
