package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.course.CourseDisplayDto;
import com.shu.onlineEducation.common.dto.course.CourseDto;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
	/*课程*/
	
	List<Course> getAllCourses();        //获取所有课程信息
	
	Page<Course> getAllCoursesByMajorId(Pageable pageable, int majorId) throws NotFoundException;     //获取专业的所有课程
	
	Page<Course> getAllCoursesByMajorIdAndNeedVip(Pageable pageable, int majorId, boolean needVip) throws NotFoundException;
	
	Page<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) throws NotFoundException;     //获取某子专业的所有课程
	
	Page<Course> getAllCoursesByNeedVipAndPreferId(Pageable pageable, boolean needVip, int preferId) throws NotFoundException;
	
	void updateCourseStatusById(int courseId, int status) throws NotFoundException;//更新课程状态
	
	void deleteCourseById(int courseId);
	
	Page<Course> getAllCoursesByTeacherId(Pageable pageable, int teacherId);//获取某老师的所有课程
	
	Page<Course> getAllCoursesByTeacherAndStatus(Pageable pageable, int teacherId);
	
	void addCourse(CourseDto courseDto) throws NotFoundException;
	
	void updateCourse(Integer courseId, CourseDto courseDto) throws NotFoundException;
	
	List<Course> getRelatedCourses(Integer courseId) throws NotFoundException;
	
	Page<Course> getCoursesWithRegex(Pageable pageable, String query);
	/*课程章节*/
	
	List<CourseChapter> getAllCourseChapterByCourseId(int courseId) throws NotFoundException;
	
	void addCourseChapter(Integer courseId, Integer chapterId, String intro) throws NotFoundException;
	
	void deleteCourseChapter(Integer courseId, Integer chapterId);
	
	/*课程章节视频*/
	
	List<CourseChapterVideo> getCourseChapterVideoByCourseChapter(Integer courseId, Integer chapterId);
	
	void deleteCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId);
	
	void addCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, String videoUrl, String videoName) throws NotFoundException;
	
	CourseDisplayDto getCourseDisplay(Integer courseId) throws NotFoundException;
}
