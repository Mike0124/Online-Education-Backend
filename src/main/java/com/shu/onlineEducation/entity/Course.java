package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private Timestamp uploadTime;
    @Column(name = "prefer_id",nullable = false)
    private Integer preferId;
    @Column(name = "need_vip")
    private Boolean needVip;
    @Column(name = "course_pic")
    private String coursePic;
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Column(name = "course_status")
    private Integer status;
    @Column(name = "course_avg_mark")
    private BigDecimal courseAvgMark;
    @Column(name = "course_watches")
    private Integer courseWatches;
//    @ManyToMany(mappedBy = "aCourses")
//    @JsonIgnore
//    private Set<Student> students = new HashSet<Student>(0);
}
