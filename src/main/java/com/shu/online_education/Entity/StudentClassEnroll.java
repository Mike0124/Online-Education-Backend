package com.shu.online_education.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "relationship_stu_class_enroll")
public class StudentClassEnroll {
    @EmbeddedId
    private StudentClassEnrollPrimaryKey studentClassEnrollPrimaryKey;
}
