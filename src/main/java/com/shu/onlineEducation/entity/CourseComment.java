package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "course_comment")
public class CourseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;
    
    @Column
    private Integer likes;
    
    @Column
    private String content;
    
    @Column
    private Timestamp time;
    
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;
    
    @Column(name = "comment_mark")
    private int commentMark;
}
