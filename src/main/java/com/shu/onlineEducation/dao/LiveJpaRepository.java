package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.common.dto.liveArrangeDto;
import com.shu.onlineEducation.entity.Live;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LiveJpaRepository extends JpaRepository<Live, Integer> {
	@Query(value = "select * from live where live_date > :liveDate or (live_date = :liveDate and live_arrange >= :liveArrange)", nativeQuery = true)
	Page<Live> findAllValidLive(Pageable pageable, @Param("liveDate") Date liveDate, @Param("liveArrange") Integer liveArrange);
	
	List<Live> findAllByLiveDateAndLiveArrange(Date liveDate, Integer liveArrange);
	
	List<Live> findAllByLiveDate(Date liveDate);
	
	List<Live> findAllByTeacherId(Integer teacherId);
	
	@Query(value = "select * from live where live_date = :liveDate and live_arrange = :liveArrange", nativeQuery = true)
	Page<Live> findAllValidLiveNow(Pageable pageable, @Param("liveDate") Date liveDate, @Param("liveArrange") Integer liveArrange);
	
	@Query(value = "select * from live where live_date = :liveDate and live_arrange > :liveArrange", nativeQuery = true)
	Page<Live> findAllValidLiveFuture(Pageable pageable, @Param("liveDate") Date liveDate, @Param("liveArrange") Integer liveArrange);
	
	@Query(value = "select * from live where live_date = :liveDate and live_arrange = :liveArrange and teacher_id = :teacherId", nativeQuery = true)
	List<Live> findAllValidLiveNowByTeacher(@Param("liveDate") Date liveDate, @Param("liveArrange") Integer liveArrange, @Param("teacherId") Integer teacherId);
	
	@Query(value = "select * from live where live_date = :liveDate and live_arrange > :liveArrange and teacher_id = :teacherId", nativeQuery = true)
	List<Live> findAllValidLiveFutureByTeacher(@Param("liveDate") Date liveDate, @Param("liveArrange") Integer liveArrange, @Param("teacherId") Integer teacherId);
}
