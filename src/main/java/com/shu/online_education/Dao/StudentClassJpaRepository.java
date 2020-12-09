package com.shu.online_education.Dao;

import com.shu.online_education.Entity.StudentClassEnroll;
import com.shu.online_education.Entity.StudentClassEnrollPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassJpaRepository extends JpaRepository<StudentClassEnroll, StudentClassEnrollPrimaryKey> {
    boolean existsByStudentClassEnrollPrimaryKey(StudentClassEnrollPrimaryKey studentClassEnrollPrimaryKey);
}
