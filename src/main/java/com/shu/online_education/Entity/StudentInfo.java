package com.shu.online_education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "phone_id")
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
}


