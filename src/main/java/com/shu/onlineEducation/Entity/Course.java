package com.shu.onlineEducation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;
    @Column(nullable = false)
    private String name;
    @Column(name = "course_num",nullable = false)
    private int courseNum;
    @Column(nullable = false)
    private String intro;
    @Column(nullable = false)
    private Date uploadTime;
    @Column(name = "prefer_id",nullable = false)
    private int preferId;
    @ManyToMany(mappedBy = "aCourses")
    @JsonIgnore
    private Set<Student> students = new HashSet<Student>(0);
}
