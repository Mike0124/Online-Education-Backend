package com.shu.onlineEducation.service.impl;

import com.alibaba.fastjson.JSON;
import com.shu.onlineEducation.common.dto.course.CourseDisplayDto;
import com.shu.onlineEducation.common.dto.course.CourseDto;
import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterPK;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideoPK;
import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	
	@Autowired
	private TeacherJpaRepository teacherJpaRepository;
	
	@Autowired
	private CourseChapterJpaRepository courseChapterJpaRepository;
	
	@Autowired
	private CourseChapterVideoJpaRepository courseChapterVideoJpaRepository;
	
	@Autowired
	private PreferJpaRepository preferJpaRepository;
	
	@Autowired
	private TaskJpaRepository taskJpaRepository;
	
	@Override
	public List<Course> getAllCourses() {
		return courseJpaRepository.findAll();
	}
	
	@Override
	public Page<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) {
		return courseJpaRepository.findAllByPreferIdAndStatus(pageable, preferId, 1);
	}
	
	@Override
	public Page<Course> getAllCoursesByNeedVipAndPreferId(Pageable pageable, boolean needVip, int preferId) {
		return courseJpaRepository.findAllByNeedVipAndPreferIdAndStatus(pageable, needVip, preferId, 1);
	}
	
	@Override
	public synchronized void updateCourseStatusById(int courseId, int status) throws NotFoundException {
		Course course = courseJpaRepository.findCourseByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		course.setStatus(status);
		courseJpaRepository.save(course);
	}
	
	@Override
	public void deleteCourseById(int courseId) {
		courseJpaRepository.deleteByCourseId(courseId);
	}
	
	@Override
	public Page<Course> getAllCoursesByTeacherId(Pageable pageable, int teacherId) {
		return courseJpaRepository.findAllByTeacherIdAndStatus(pageable, teacherId, 1);
	}
	
	@Override
	public void addCourse(CourseDto courseDto) throws NotFoundException {
		Teacher teacher = teacherJpaRepository.findTeacherByUserId(courseDto.getTeacherId());
		Prefer prefer = preferJpaRepository.findPreferByPreferId(courseDto.getPreferId());
		if (teacher == null || prefer == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Course course = new Course();
		course.setName(courseDto.getName());
		course.setTeacherId(teacher.getUserId());
		course.setPreferId(prefer.getPreferId());
		course.setIntro(courseDto.getIntro());
		course.setStatus(0);
		course.setNeedVip(courseDto.getNeedVip());
		course.setUploadTime(DateUtil.getNowTimeStamp());
		course.setCoursePic(courseDto.getCoursePicUrl());
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public void updateCourse(Integer courseId, CourseDto courseDto) throws NotFoundException {
		Prefer prefer = preferJpaRepository.findPreferByPreferId(courseDto.getPreferId());
		if (prefer == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Course course = courseJpaRepository.findCourseByCourseId(courseId);
		course.setName(courseDto.getName());
		course.setPreferId(prefer.getPreferId());
		course.setIntro(courseDto.getIntro());
		course.setNeedVip(courseDto.getNeedVip());
		course.setCoursePic(courseDto.getCoursePicUrl());
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public List<CourseChapter> getAllCourseChapterByCourseId(int courseId) throws NotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		return courseChapterJpaRepository.findAllByCourseId(courseId);
	}
	
	@Override
	public void addCourseChapter(Integer courseId, Integer chapterId, String intro) throws NotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		CourseChapter courseChapter = new CourseChapter();
		courseChapter.setCourseChapterPK(new CourseChapterPK(courseId, chapterId));
		courseChapter.setChapterIntro(intro);
		courseChapterJpaRepository.saveAndFlush(courseChapter);
	}
	
	@Override
	public void deleteCourseChapter(Integer courseId, Integer chapterId) {
		courseChapterJpaRepository.deleteByCourseIdAndChapterId(courseId, chapterId);
	}
	
	@Override
	public List<CourseChapterVideo> getCourseChapterVideoByCourseChapter(Integer courseId, Integer chapterId) {
		return courseChapterVideoJpaRepository.findByCourseChapter(courseId, chapterId);
	}
	
	@Override
	public void deleteCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId) {
		courseChapterVideoJpaRepository.deleteByCourseIdAndChapterIdandAndVideoId(courseId, chapterId, videoId);
	}
	
	@Override
	public void addCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, String videoUrl, String videoName) throws NotFoundException {
		CourseChapterVideo courseChapterVideo = new CourseChapterVideo();
		if (courseChapterJpaRepository.findByCourseIdAndChapterId(courseId, chapterId) != null) {
			courseChapterVideo.setCourseChapterVideoPK(new CourseChapterVideoPK(courseId, chapterId, videoId));
			courseChapterVideo.setVideoUrl(videoUrl);
			courseChapterVideo.setVideoName(videoName);
			courseChapterVideoJpaRepository.saveAndFlush(courseChapterVideo);
		} else {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
	}
	
	@Override
	public CourseDisplayDto getCourseDisplay(Integer courseId) throws NotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		CourseDisplayDto courseDisplayDto = new CourseDisplayDto();
		List<CourseChapter> courseChapterList = courseChapterJpaRepository.findAllByCourseId(courseId);
		for (CourseChapter courseChapter : courseChapterList) {
			Map<String, List> map = new LinkedHashMap<>();
			map.put("VideoList", courseChapterVideoJpaRepository.findByCourseChapter(courseChapter));
			map.put("TaskList", taskJpaRepository.findByCourseChapter(courseChapter));
			courseDisplayDto.put(JSON.toJSONString(courseChapter), map);
		}
		return courseDisplayDto;
	}
}
