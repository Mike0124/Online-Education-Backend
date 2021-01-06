package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherJpaRepository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {
    boolean existsByUserId(Integer userId);

    boolean existsByPhoneId(String phoneId);
    
    Teacher findTeacherByPhoneId(String phoneId);
    @Transactional
    void deleteTeacherByUserId(Integer userId);

    Teacher findTeacherByUserId(Integer userId);
}
