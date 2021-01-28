package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterPK;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseChapterVideoJpaRepository extends JpaRepository<CourseChapterVideo, CourseChapterPK> {
	
	List<CourseChapterVideo> findByCourseChapter(CourseChapter courseChapter);
	
	@Query(value = "select * from course_chapter_video where course_id = ?1 and chapter_id = ?2", nativeQuery = true)
	List<CourseChapterVideo> findByCourseChapter(Integer courseId, Integer chapterId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from course_chapter_video where course_id = ?1 and chapter_id = ?2 and video_id =?3", nativeQuery = true)
	void deleteByCourseIdAndChapterIdandAndVideoId(int courseId, int chapterId, int videoId);
}
