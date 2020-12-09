package com.shu.online_education.Dao;

import com.shu.online_education.Entity.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherJpaRepository extends JpaRepository<TeacherInfo, Integer>, JpaSpecificationExecutor<TeacherInfo> {
    boolean existsByUserId(int userId);

    boolean existsByPhoneId(String phoneId);

    @Transactional
    void deleteTeacherInfoByUserId(int userId);

    TeacherInfo findTeacherInfoByUserId(int userId);
}
