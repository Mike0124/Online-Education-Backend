package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.ItemCFResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCFResultJpaRepository extends JpaRepository<ItemCFResult,Integer> {
	ItemCFResult findByStudentId(Integer studentId);
}
