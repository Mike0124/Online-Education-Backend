package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.course.WatchRecordDto;
import com.shu.onlineEducation.dao.CourseChapterVideoJpaRepository;
import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.dao.WatchRecordJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.WatchRecord;
import com.shu.onlineEducation.service.WatchRecordService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WatchRecordServiceImpl implements WatchRecordService {
	@Autowired
	private WatchRecordJpaRepository watchRecordJpaRepository;
	@Autowired
	private CourseChapterVideoJpaRepository chapterVideoJpaRepository;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	
	@Override
	public Page<WatchRecord> getAllByStudent(Pageable pageable, Integer studentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return watchRecordJpaRepository.findAllByStudent(pageable, student);
	}
	
	@Override
	public void addWatchRecords(WatchRecordDto watchRecordDto) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(watchRecordDto.getStudentId());
		Course course = courseJpaRepository.findByCourseId(watchRecordDto.getCourseId());
		CourseChapterVideo video = chapterVideoJpaRepository.findByCourseIdAndChapterIdAndVideoId(watchRecordDto.getCourseId(), watchRecordDto.getChapterId(), watchRecordDto.getVideoId());
		if (student == null || video == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		//判断是否存在该课程的观看记录
		WatchRecord watchRecord = watchRecordJpaRepository.findByStudentAndCourse(watchRecordDto.getStudentId(), watchRecordDto.getCourseId());
		if (watchRecord == null) {
			//从已删除的课程中判断学生是否看过课程,没看过则课程观看数量+1
			if (watchRecordJpaRepository.findDeletedByStudentAndCourse(watchRecordDto.getStudentId(), watchRecordDto.getCourseId()) == null) {
				course.setCourseWatches(course.getCourseWatches() + 1);
				courseJpaRepository.saveAndFlush(course);
			}
			watchRecord = new WatchRecord();
			watchRecord.setStudentId(watchRecordDto.getStudentId());
		}
		watchRecord.setDeleted(0);
		watchRecord.setPicUrl(course.getCoursePic());
		watchRecord.setCourseChapterVideo(video);
		watchRecordJpaRepository.save(watchRecord);
	}
	
	@Override
	public void deleteWatchRecord(Integer watchRecordId) {
		watchRecordJpaRepository.deleteById(watchRecordId);
	}
	
}
