package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.StudentLikeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentLikeCommentJpaRepository extends JpaRepository<StudentLikeComment, Integer> {
	Page<StudentLikeComment> findByStudent(Pageable pageable, Student student);
	
	@Query(value = "select * from stu_like_comment where student_id = :studentId and comment_id in (:commentIds);", nativeQuery = true)
	List<StudentLikeComment> findByStudentAndComments(@Param("studentId") Integer studentId, @Param("commentIds") List<Integer> commentIds);
	
	StudentLikeComment findByStudentAndCourseComment(Student student, CourseComment courseComment);
	
	@Transactional
	void deleteByStudentAndCourseComment(Student student, CourseComment courseComment);
}

