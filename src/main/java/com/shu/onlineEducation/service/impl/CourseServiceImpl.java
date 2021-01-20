package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.CourseChapterJpaRepository;
import com.shu.onlineEducation.dao.CourseChapterVideoJpaRepository;
import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterPK;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideoPK;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
	
	
	@Override
	public List<Course> getAllCourses() {
		return courseJpaRepository.findAll();
	}
	
	@Override
	public List<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) {
		return courseJpaRepository.findAllByPreferIdAndStatus(pageable, preferId, 1);
	}
	
	@Override
	public List<Course> getAllCoursesByNeedVipAndPreferId(Pageable pageable, boolean needVip, int preferId) {
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
	public List<Course> getAllCoursesByTeacherId(Pageable pageable, int teacherId) {
		return courseJpaRepository.findAllByTeacherIdAndStatus(pageable, teacherId, 1);
	}
	
	@Override
	public void addCourse(Integer teacherId, Integer preferId, String name, String intro, Timestamp uploadTime, String coursePicUrl, boolean needVip) throws NotFoundException {
		Course course = new Course();
		course.setName(name);
		try {
			course.setTeacherId(teacherId);
			course.setPreferId(preferId);
		} catch (DataIntegrityViolationException e) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		course.setIntro(intro);
		course.setStatus(0);
		course.setNeedVip(needVip);
		course.setUploadTime(uploadTime);
		course.setCoursePic(coursePicUrl);
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public void updateCourse(Integer courseId, Integer preferId, String name, String intro, String coursePicUrl, boolean needVip) throws NotFoundException {
		Course course = courseJpaRepository.findCourseByCourseId(courseId);
		course.setName(name);
		try {
			course.setPreferId(preferId);
		} catch (DataIntegrityViolationException e) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		course.setIntro(intro);
		course.setNeedVip(needVip);
		course.setCoursePic(coursePicUrl);
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public List<CourseChapter> getAllCourseChapterByCourseId(Pageable pageable, int courseId) throws NotFoundException {
		if (!courseJpaRepository.existsByCourseId(courseId)) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		return courseChapterJpaRepository.findAllByCourseId(pageable, courseId);
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
}
