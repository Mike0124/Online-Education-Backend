package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    boolean existsByUserId(int userId);

    boolean existsByPhoneId(String phoneId);

    @Transactional
    void deleteStudentByUserId(int userId);

    Student findByUserId(int userId);

    Student findByPhoneId(String phoneId);
}