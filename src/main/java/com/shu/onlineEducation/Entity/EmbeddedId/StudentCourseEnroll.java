package com.shu.onlineEducation.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "relationship_stu_course_enroll")
public class StudentCourseEnroll {
    @EmbeddedId
    private StudentCourseEnrollPrimaryKey studentCourseEnrollPrimaryKey;
}