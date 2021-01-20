package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentPreferenceRepository extends JpaRepository<StudentPreference, StudentPreferencePK> {
	@Query(value = "select * from relationship_stu_prefer where student_id = ?1", nativeQuery = true)
	List<StudentPreference> findAllByStudentId(int userId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from relationship_stu_prefer where student_id = ?1", nativeQuery = true)
	void deleteAllByStudentId(int userId);
}
