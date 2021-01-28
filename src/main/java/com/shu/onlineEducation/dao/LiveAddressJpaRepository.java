package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.LiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveAddressJpaRepository extends JpaRepository<LiveAddress, Integer> {

}
