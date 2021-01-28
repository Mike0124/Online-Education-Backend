package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveJpaRepository extends JpaRepository<Live, Integer> {

}
