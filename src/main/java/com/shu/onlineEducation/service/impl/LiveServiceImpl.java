package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.LiveDto;
import com.shu.onlineEducation.dao.LiveJpaRepository;
import com.shu.onlineEducation.entity.Live;
import com.shu.onlineEducation.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class LiveServiceImpl implements LiveService {
	@Autowired
	LiveJpaRepository liveJpaRepository;
	
	@Transactional
	@Override
	public void addLive(LiveDto liveDto) {
		Live live = new Live();
		live.setAddressId(liveDto.getAddressId());
		live.setIntro(liveDto.getLiveIntro());
		live.setLiveArrange(liveDto.getLiveArrange());
		live.setLiveDate(liveDto.getLiveDate());
		live.setLivePicUrl(liveDto.getLivePicUrl());
		live.setLiveName(liveDto.getLiveName());
		live.setTeacherId(liveDto.getTeacherId());
		liveJpaRepository.saveAndFlush(live);
	}
}
