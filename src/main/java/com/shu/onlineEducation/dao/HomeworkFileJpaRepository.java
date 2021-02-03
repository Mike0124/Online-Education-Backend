package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.HomeworkFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface HomeworkFileJpaRepository extends JpaRepository<HomeworkFile, Integer> {
	@Query(value = "select * from homework_file where homework_id = :homeworkId", nativeQuery = true)
	List<HomeworkFile> findByHomework(Integer homeworkId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from homework_file where homework_file_id = :homeworkFileId", nativeQuery = true)
	void deleteByHomeworkFileId(Integer homeworkFileId);
}
