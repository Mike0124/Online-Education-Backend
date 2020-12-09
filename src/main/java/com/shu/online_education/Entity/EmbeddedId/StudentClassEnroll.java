package com.shu.online_education.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "relationship_stu_class_enroll")
public class StudentClassEnroll {
    @EmbeddedId
    private StudentClassEnrollPrimaryKey studentClassEnrollPrimaryKey;
}