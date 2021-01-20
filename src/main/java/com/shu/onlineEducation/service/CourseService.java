package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface CourseService {
	/*课程*/
	
	List<Course> getAllCourses();        //获取所有课程信息
	
	List<Course> getAllCoursesByPreferId(Pageable pageable, int preferId) throws NotFoundException;     //获取某子专业的所有课程
	
	List<Course> getAllCoursesByNeedVipAndPreferId(Pageable pageable, boolean needVip, int preferId) throws NotFoundException;
	
	void updateCourseStatusById(int courseId, int status) throws NotFoundException;//更新课程状态
	
	void deleteCourseById(int courseId);
	
	List<Course> getAllCoursesByTeacherId(Pageable pageable, int teacherId);//获取某老师的所有课程
	
	void addCourse(Integer teacherId, Integer preferId, String name, String intro, Timestamp uploadTime, String coursePicUrl, boolean needVip) throws NotFoundException;
	
	void updateCourse(Integer courseId, Integer preferId, String name, String intro, String coursePicUrl, boolean needVip) throws NotFoundException;
	
	/*课程章节*/
	
	List<CourseChapter> getAllCourseChapterByCourseId(Pageable pageable, int courseId) throws NotFoundException;
	
	void addCourseChapter(Integer courseId, Integer chapterId, String intro) throws NotFoundException;
	
	void deleteCourseChapter(Integer courseId, Integer chapterId);
	
	/*课程章节视频*/
	
	List<CourseChapterVideo> getCourseChapterVideoByCourseChapter(Integer courseId, Integer chapterId);
	
	void deleteCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId);
	
	void addCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, String videoUrl, String videoName) throws NotFoundException;
}
