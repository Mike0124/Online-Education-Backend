package com.shu.onlineEducation.entity;

import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    
    @Column(name = "course_status")
    private Integer status;
    
    @Column(name = "course_avg_mark")
    private BigDecimal courseAvgMark;
    
    @Column(name = "course_watches")
    private Integer courseWatches;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "user_id")
    private Teacher teacher;
//    @ManyToOne
//    @JoinColumn(name = "prefer_id",referencedColumnName = "prefer_id")
//    private Prefer prefer;
    
    
    public String getUploadTime(){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(uploadTime);
    }
}
