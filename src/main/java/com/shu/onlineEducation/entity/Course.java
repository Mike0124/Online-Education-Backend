package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;
    @Column(nullable = false)
    private String name;
    @Column(name = "course_num",nullable = false)
    private Integer num;
    @Column(nullable = false)
    private String intro;
    @Column(nullable = false)
    private Date uploadTime;
    @Column(name = "prefer_id",nullable = false)
    private Integer preferId;
    @Column(name = "course_status")
    private Integer status;
//    @ManyToMany(mappedBy = "aCourses")
//    @JsonIgnore
//    private Set<Student> students = new HashSet<Student>(0);
}
