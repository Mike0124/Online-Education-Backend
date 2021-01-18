package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Prefer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferJpaRepository extends JpaRepository<Prefer,Integer> {
	Prefer findPreferByPreferId(int preferId);
}
