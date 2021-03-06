package com.shu.onlineEducation.service.impl;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
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
	public List<Course> getAllCoursesOrderByWatches(Pageable pageable) {
		return courseJpaRepository.findAllOrderBy(pageable);
	}
	
	@Override
	public Page<Course> getAllCoursesByMajorId(Pageable pageable, int majorId) throws NotFoundException {
		return courseJpaRepository.findByMajor(pageable, majorId);
	}
	
	@Override
	public Page<Course> getAllCoursesByMajorIdAndNeedVip(Pageable pageable, int majorId, boolean needVip) throws NotFoundException {
		return courseJpaRepository.findByMajorAndNeedVip(pageable, majorId, needVip);
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
	public Page<Course> getCourseByStatus(Pageable pageable, Integer status) {
		return courseJpaRepository.findAllByStatus(pageable, status);
	}
	
	@Override
	public Page<Course> getCourseByTeacherAndStatus(Pageable pageable, Integer teacherId, Integer status) {
		return courseJpaRepository.findAllByTeacherIdAndStatus(pageable, teacherId, status);
	}
	
	@Override
	public void updateCourseStatusById(int courseId, int status) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
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
		return courseJpaRepository.findAllByTeacherId(pageable, teacherId);
	}
	
	@Override
	public Page<Course> getAllCoursesByTeacherAndStatus(Pageable pageable, int teacherId) {
		return courseJpaRepository.findAllByTeacherIdAndStatus(pageable, teacherId, 1);
	}
	
	@Override
	public void addCourse(CourseDto courseDto) throws NotFoundException {
		Teacher teacher = teacherJpaRepository.findTeacherByUserId(courseDto.getTeacherId());
		Prefer prefer = preferJpaRepository.findByPreferId(courseDto.getPreferId());
		if (teacher == null || prefer == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Course course = new Course();
		course.setName(courseDto.getName());
		course.setTeacherId(teacher.getUserId());
		course.setPreferId(prefer.getPreferId());
		course.setIntro(courseDto.getIntro());
		course.setCourseWatches(0);
		course.setStatus(0);
		course.setNeedVip(courseDto.getNeedVip());
		course.setCoursePic(courseDto.getCoursePicUrl());
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public void updateCourse(Integer courseId, CourseDto courseDto) throws NotFoundException {
		Prefer prefer = preferJpaRepository.findByPreferId(courseDto.getPreferId());
		if (prefer == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		Course course = courseJpaRepository.findByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		course.setName(courseDto.getName());
		course.setPreferId(prefer.getPreferId());
		course.setIntro(courseDto.getIntro());
		course.setNeedVip(courseDto.getNeedVip());
		course.setCoursePic(courseDto.getCoursePicUrl());
		courseJpaRepository.saveAndFlush(course);
	}
	
	@Override
	public List<Course> getRelatedCourses(Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		return courseJpaRepository.findRelatedCourses(course.getCourseId(), course.getPreferId());
	}
	
	@Override
	public Page<Course> getCoursesWithRegex(Pageable pageable, String query) {
		// 使用HanLP分词
		List<Term> termList = StandardTokenizer.segment(query);
		// 拼接正则字符串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < termList.size(); i++) {
			String word = termList.get(i).word;
			if (i != termList.size() - 1) {
				sb.append(word).append("|");
			} else {
				sb.append(word);
			}
		}
		String regex = sb.toString();
		sb.insert(0, ".*(");
		sb.append(").*");
		Page<Course> courses = courseJpaRepository.findWithRegex(pageable, regex);
		List<Course> list = courses.getContent();
		list.forEach(course -> {
			String newName = course.getName().replaceAll(regex, "<font color='#409eff'>$0</font>");
			course.setName(newName);
		});
		return courses;
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
		courseChapterVideoJpaRepository.deleteByCourseIdAndChapterIdAndVideoId(courseId, chapterId, videoId);
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
