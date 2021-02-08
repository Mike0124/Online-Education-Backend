package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<Admin,Integer> {
	Admin findByPhoneId(String phoneId);
}
