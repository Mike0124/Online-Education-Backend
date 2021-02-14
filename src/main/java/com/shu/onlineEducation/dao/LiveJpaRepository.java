package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.common.dto.liveArrangeDto;
import com.shu.onlineEducation.entity.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LiveJpaRepository extends JpaRepository<Live, Integer> {
    @Query(value = "select live_arrange, count(live_arrange) as liveCount from live where live_date =?1 group by live_arrange", nativeQuery = true)
    List<liveArrangeDto> findArrangeIsValidInDay(Date liveDate);

    List<Live> findAllByLiveDateAndLiveArrange(Date liveDate, Integer liveArrange);

    List<Live> findAllByLiveDate(Date liveDate);

    List<Live> findAllByTeacher(Integer teacherId);
}
