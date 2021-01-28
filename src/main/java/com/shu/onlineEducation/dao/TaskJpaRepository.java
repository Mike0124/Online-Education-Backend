package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import com.shu.onlineEducation.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Integer> {
	List<Task> findByCourseChapter(CourseChapter courseChapter);
	
	@Query(value = "select * from task where task_id = :taskId", nativeQuery = true)
	Task findTaskById(Integer taskId);
	
	@Query(value = "select * from task where course_id = :courseId and chapter_id = :chapterId", nativeQuery = true)
	List<Task> findByCourseChapter(Integer courseId, Integer chapterId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from task where task_id = :taskId", nativeQuery = true)
	void deleteByTaskId(int taskId);
}
