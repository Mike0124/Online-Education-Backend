package com.shu.online_education.Dao;

import com.shu.online_education.Entity.EmbeddedId.StudentPreference;
import com.shu.online_education.Entity.EmbeddedId.StudentPreferencePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPreferenceRepository extends JpaRepository<StudentPreference,StudentPreferencePrimaryKey> {
	List<StudentPreference> findAllByStudentId(int user_id);
}
