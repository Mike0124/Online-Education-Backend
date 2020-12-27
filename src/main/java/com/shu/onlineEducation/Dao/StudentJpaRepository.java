package com.shu.onlineEducation.Dao;

import com.shu.onlineEducation.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    boolean existsByUserId(int userId);

    boolean existsByPhoneId(String phoneId);

    @Transactional
    void deleteStudentInfoByUserId(int userId);

    Student findStudentInfoByUserId(int userId);
}