package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CourseChapterJpaRepository extends JpaRepository<CourseChapter, CourseChapterPK> {
	@Query(value = "select * from course_chapter where course_id = ?1 order by chapter_id", nativeQuery = true)
	List<CourseChapter> findAllByCourseId(Integer courseId);
	
	@Query(value = "select * from course_chapter where course_id = ?1 and chapter_id = ?2", nativeQuery = true)
	CourseChapter findByCourseIdAndChapterId(Integer courseId, Integer chapterId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from course_chapter where course_id = ?1 and chapter_id = ?2", nativeQuery = true)
	void deleteByCourseIdAndChapterId(int courseId, int chapterId);
}
