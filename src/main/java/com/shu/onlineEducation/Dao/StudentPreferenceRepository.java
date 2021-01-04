package com.shu.onlineEducation.Dao;

import com.shu.onlineEducation.Entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.Entity.EmbeddedId.StudentPreferencePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPreferenceRepository extends JpaRepository<StudentPreference,StudentPreferencePrimaryKey> {
	@Query(value = "select * from relationship_stu_prefer where student_id = ?1", nativeQuery = true)
	List<StudentPreference> findAllByStudentId(int userId);
}
