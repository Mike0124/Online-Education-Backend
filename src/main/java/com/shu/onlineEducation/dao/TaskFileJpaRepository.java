package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.TaskFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskFileJpaRepository extends JpaRepository<TaskFile, Integer> {
	@Query(value = "select * from task_file where task_id = :taskId", nativeQuery = true)
	List<TaskFile> findByTask(Integer taskId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from task_file where task_id = :taskFileId", nativeQuery = true)
	void deleteByTaskFileId(int taskFileId);
}
