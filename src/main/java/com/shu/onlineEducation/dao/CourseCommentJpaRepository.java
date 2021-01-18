package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CourseCommentJpaRepository extends JpaRepository<CourseComment, Integer> {
    @Query(value = "select avg(comment_mark) from course_comment where course_id =:courseId", nativeQuery = true)
    BigDecimal getCommentMarkAvg(@Param("courseId") int courseId);
}
