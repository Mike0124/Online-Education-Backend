package com.shu.onlineEducation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers", uniqueConstraints = @UniqueConstraint(columnNames = "phone_id"))
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "wechat_id")
    private String wechatId;
    @Column
    private String name;
    @Column(name = "phone_id", nullable = false)
    private String phoneId;
    @Column
    private String sex;
    @Column
    private String school;
    @Column(name = "major_id")
    private String majorId;
    @Column
    private String password;
    @Column(name = "teacher_pic")
    private String teacherPic;
    @Column(name = "teacher_status")
    private Integer teacherStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<Course> aCourses = new HashSet<Course>(0);
}
