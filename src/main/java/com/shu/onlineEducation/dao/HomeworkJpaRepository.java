package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Homework;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HomeworkJpaRepository extends JpaRepository<Homework, Integer> {
	Homework findByHomeworkId(Integer homeworkId);
	
	Homework findByTaskAndStudent(Task task, Student student);
	
	Page<Homework> findByStudent(Pageable pageable, Student student);
	
	Page<Homework> findByTask(Pageable pageable, Task task);
	
	Page<Homework> findByTaskAndStatus(Pageable pageable, Task task, Integer status);
	
	@Transactional
	void deleteByHomeworkId(Integer homeworkId);
}
