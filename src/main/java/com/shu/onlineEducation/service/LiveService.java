package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.LiveDto;
import com.shu.onlineEducation.common.dto.liveArrangeDto;
import com.shu.onlineEducation.entity.Live;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface LiveService {

    void addLive(LiveDto liveDto) throws ExistedException;

    List<liveArrangeDto> isValidInDay(Date liveDate);

    void modifyLive(LiveDto liveDto, Integer liveId) throws NotFoundException;

    void deleteLive(Integer liveId);

    List<Live> findAllLiveByTeacherId(Integer teacherId);

    List<Live> findAllLiveByDate(Date liveDate);

    Page<Live> findAllValidLive(Pageable pageable, Date liveDate, Integer liveArrange);

    Page<Live> findAllValidLiveNow(Pageable pageable, Date liveDate, Integer liveArrange);

    Page<Live> findAllValidLiveFuture(Pageable pageable, Date liveDate, Integer liveArrange);
}
