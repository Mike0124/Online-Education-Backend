package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.StudentLikeCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface StudentLikeCourseJpaRepository extends JpaRepository<StudentLikeCourse, Integer> {
	StudentLikeCourse findByStudentAndCourse(Student student, Course course);
	
	Page<StudentLikeCourse> findByStudent(Student student, Pageable pageable);
	
	@Transactional
	void deleteByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
