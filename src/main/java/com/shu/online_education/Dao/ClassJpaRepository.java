package com.shu.online_education.Dao;

import com.shu.online_education.Entity.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassJpaRepository extends JpaRepository<ClassInfo,Integer>, JpaSpecificationExecutor<ClassInfo> {
    boolean existsByClassId(int class_id);
}
