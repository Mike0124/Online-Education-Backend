package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.ItemCF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCFJpaRepository extends JpaRepository<ItemCF,Integer> {
	List<ItemCF> findByStudentId(Integer studentId);
	
	ItemCF findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
