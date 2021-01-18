package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorJpaRepository extends JpaRepository<Major,Integer> {
	Major findMajorByMajorId(int majorId);
}
