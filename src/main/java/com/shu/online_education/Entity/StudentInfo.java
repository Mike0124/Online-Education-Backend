package com.shu.online_education.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "students", uniqueConstraints = @UniqueConstraint(columnNames = "phone_id"))
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
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
    @Column
    private String major;
    @Column
    private int grade;
    @Column
    private String password;

    //多对多关系映射
    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinTable(name = "relationship_stu_class_enroll",
            joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "user_id")},
            //中间表字段stu_id关联当前表的user_id)
            inverseJoinColumns = {@JoinColumn(name = "class_id", referencedColumnName = "class_id")}
            )
    @JsonIgnore
    private Set<ClassInfo> ClassInfos = new HashSet<ClassInfo>(0);
}


