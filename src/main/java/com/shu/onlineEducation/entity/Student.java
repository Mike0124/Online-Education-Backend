package com.shu.onlineEducation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students", uniqueConstraints = @UniqueConstraint(columnNames = "phone_id"))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "wechat_id")
    private String wechatId;
    @Column(name = "nickname")
    private String nickName;
    @Column(name = "phone_id",nullable = false)
    private String phoneId;
    @Column
    private String sex;
    @Column
    private String school;
    @Column(name = "major_id")
    private Integer majorId;
    @Column
    private Integer grade;
    @Column
    private String password;
    @Column(name = "is_vip")
    private boolean isVip;
    @Column(name = "student_pic")
    private byte[] studentPic;

    //多对多关系映射
//    @ManyToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(name = "relationship_stu_course_enroll",
//            joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "user_id")},
//            //中间表字段stu_id关联当前表的user_id)
//            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "course_id")}
//            )
//    @JsonIgnore
//    private Set<Course> aCourses = new HashSet<Course>(0);
}


