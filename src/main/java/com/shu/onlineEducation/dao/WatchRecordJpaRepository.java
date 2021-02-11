package com.shu.onlineEducation.dao;

import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.WatchRecord;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WatchRecordJpaRepository extends JpaRepository<WatchRecord, Integer> {
	Page<WatchRecord> findAllByStudent(Pageable pageable, Student student);
	
	@Query(value = "select * from course_watch_record where student_id = :studentId and course_id = :courseId and deleted = 0", nativeQuery = true)
	WatchRecord findByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}
