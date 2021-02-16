package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.LiveDto;
import com.shu.onlineEducation.common.dto.liveArrangeDto;
import com.shu.onlineEducation.utils.ResultTransfomer.DefineResultTransformer;
import com.shu.onlineEducation.dao.LiveJpaRepository;
import com.shu.onlineEducation.entity.Live;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.LiveService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

@Service
public class LiveServiceImpl implements LiveService {
    @Autowired
    LiveJpaRepository liveJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AppProperties appProperties;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ExistedException.class)
    @Override
    public void addLive(LiveDto liveDto) throws ExistedException {
        if (liveJpaRepository.findAllByLiveDateAndLiveArrange(liveDto.getLiveDate(), liveDto.getLiveArrange()).size() < appProperties.getMax_live_in_one_arrange()) {
            Live live = new Live();
            live.setAddressId(liveDto.getAddressId());
            live.setIntro(liveDto.getLiveIntro());
            live.setLiveArrange(liveDto.getLiveArrange());
            live.setLiveDate(liveDto.getLiveDate());
            live.setLivePicUrl(liveDto.getLivePicUrl());
            live.setLiveName(liveDto.getLiveName());
            live.setTeacherId(liveDto.getTeacherId());
            liveJpaRepository.saveAndFlush(live);
        } else {
            throw new ExistedException(ResultCode.COURSE_LIVE_EXISTED);
        }
    }

    @Override
    public List<liveArrangeDto> isValidInDay(Date liveDate) {
        String sql = "select live_arrange, count(live_arrange) as liveCount from live where live_date = '" + liveDate.toString() + "' group by live_arrange";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        //采用原生sql,采用自定义的结果转换器DefineResultTransformer
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(new DefineResultTransformer<>(liveArrangeDto.class));
        return nativeQuery.getResultList();
    }


    @Override
    public void modifyLive(LiveDto liveDto, Integer liveId) throws NotFoundException {
        if (liveJpaRepository.existsById(liveId)) {
            Live live = new Live();
            live.setLiveId(liveId);
            live.setAddressId(liveDto.getAddressId());
            live.setIntro(liveDto.getLiveIntro());
            live.setLiveArrange(liveDto.getLiveArrange());
            live.setLiveDate(liveDto.getLiveDate());
            live.setLivePicUrl(liveDto.getLivePicUrl());
            live.setLiveName(liveDto.getLiveName());
            live.setTeacherId(liveDto.getTeacherId());
            liveJpaRepository.saveAndFlush(live);
        } else {
            throw new NotFoundException(ResultCode.COURSE_LIVE_NOT_FOUND);
        }
    }

    @Override
    public void deleteLive(Integer liveId) {
        liveJpaRepository.deleteById(liveId);
    }

    @Override
    public List<Live> findAllLiveByTeacherId(Integer teacherId) {
        return liveJpaRepository.findAllByTeacherId(teacherId);
    }

    @Override
    public List<Live> findAllLiveByDate(Date liveDate) {
        return liveJpaRepository.findAllByLiveDate(liveDate);
    }
}
