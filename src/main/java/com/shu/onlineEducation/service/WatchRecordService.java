package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.course.WatchRecordDto;
import com.shu.onlineEducation.entity.WatchRecord;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WatchRecordService {
	Page<WatchRecord> getAllByStudent(Pageable pageable, Integer studentId) throws NotFoundException;
	
	void addWatchRecords(WatchRecordDto watchRecordDto) throws NotFoundException;
	
	void deleteWatchRecord(Integer watchRecordId);
	
	WatchRecord getByStudentAndCourse(Integer userId, Integer courseId) throws NotFoundException;
}
