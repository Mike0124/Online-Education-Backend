package com.shu.onlineEducation.entity;

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
    
    @ManyToOne
    @JoinColumn(name = "major_id",referencedColumnName = "major_id")
    Major major;
    
    @Column
    private Integer grade;
    
    @Column
    private String password;
    
    @Column(name = "is_vip")
    private boolean isVip;
    
    @Column(name = "student_pic")
    private byte[] studentPic;
}


