package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.EmbeddedId.StudentCourseEnroll;
import com.shu.onlineEducation.entity.EmbeddedId.StudentCourseEnrollPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseJpaRepository extends JpaRepository<StudentCourseEnroll, StudentCourseEnrollPrimaryKey> {
	boolean existsByStudentCourseEnrollPrimaryKey(StudentCourseEnrollPrimaryKey studentCourseEnrollPrimaryKey);
}
