package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Major;
import com.shu.onlineEducation.entity.Prefer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferJpaRepository extends JpaRepository<Prefer, Integer> {
	Prefer findByPreferId(int preferId);
	
	Prefer findByPreferContent(String preferContent);
	
	List<Prefer> findByMajor(Major major);
}
