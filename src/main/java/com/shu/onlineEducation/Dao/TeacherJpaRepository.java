package com.shu.onlineEducation.Dao;

import com.shu.onlineEducation.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherJpaRepository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {
    boolean existsByUserId(int userId);

    boolean existsByPhoneId(String phoneId);

    @Transactional
    void deleteTeacherInfoByUserId(int userId);

    Teacher findTeacherInfoByUserId(int userId);
}
