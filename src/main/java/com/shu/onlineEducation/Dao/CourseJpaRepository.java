package com.shu.onlineEducation.Dao;

import com.shu.onlineEducation.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course,Integer>, JpaSpecificationExecutor<Course> {
}
