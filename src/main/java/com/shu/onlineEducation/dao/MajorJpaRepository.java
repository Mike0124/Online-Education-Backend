package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MajorJpaRepository extends JpaRepository<Major,Integer> {
	Major findMajorByMajorId(int majorId);

	boolean existsByMajorContent(String majorContent);

	boolean existsByMajorId(int majorId);

	@Transactional
	void deleteMajorByMajorId(int majorId);
}
