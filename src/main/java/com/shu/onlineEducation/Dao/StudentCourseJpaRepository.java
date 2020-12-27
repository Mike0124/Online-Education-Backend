package com.shu.onlineEducation.Dao;

import com.shu.onlineEducation.Entity.EmbeddedId.StudentCourseEnroll;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentCourseEnrollPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseJpaRepository extends JpaRepository<StudentCourseEnroll, StudentCourseEnrollPrimaryKey> {
	boolean existsByStudentCourseEnrollPrimaryKey(StudentCourseEnrollPrimaryKey studentCourseEnrollPrimaryKey);
}
