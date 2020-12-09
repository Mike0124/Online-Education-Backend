package com.shu.online_education.Dao;

import com.shu.online_education.Entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentInfo, Integer>, JpaSpecificationExecutor<StudentInfo> {
    boolean existsByUserId(int userId);

    boolean existsByPhoneId(String phoneId);

    @Transactional
    void deleteStudentInfoByUserId(int userId);

    StudentInfo findStudentInfoByUserId(int userId);
}