package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CourseCommentJpaRepository extends JpaRepository<CourseComment, Integer> {
    @Query("select avg(a.commentMark) from CourseComment a where a.commentId = #{courseId}")
    BigDecimal getCommentMarkAvg(int courseId);
}
