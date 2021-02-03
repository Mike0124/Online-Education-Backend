package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Homework;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface HomeworkJpaRepository extends JpaRepository<Homework, Integer> {
	@Query(value = "select * from homework where task_id = :taskId and student_id = :studentId", nativeQuery = true)
	Homework findByTaskIdAndStudentId(Integer taskId, Integer studentId);
	
	@Query(value = "select * from homework where task_id = :taskId", nativeQuery = true)
	List<Homework> findByTask(Pageable pageable, Integer taskId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from homework where homework_id = :homeworkId", nativeQuery = true)
	void deleteByHomeworkId(Integer homeworkId);
}
