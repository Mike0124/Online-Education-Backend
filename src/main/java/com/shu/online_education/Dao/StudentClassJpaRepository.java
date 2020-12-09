package com.shu.online_education.Dao;

import com.shu.online_education.Entity.EmbeddedId.StudentClassEnroll;
import com.shu.online_education.Entity.EmbeddedId.StudentClassEnrollPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassJpaRepository extends JpaRepository<StudentClassEnroll, StudentClassEnrollPrimaryKey> {
	boolean existsByStudentClassEnrollPrimaryKey(StudentClassEnrollPrimaryKey studentClassEnrollPrimaryKey);
}
