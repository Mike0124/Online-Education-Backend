package com.shu.online_education.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers", uniqueConstraints = @UniqueConstraint(columnNames = "phone_id"))
public class TeacherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
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
    @Column
    private String major;
    @Column
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Set<ClassInfo> classInfos = new HashSet<ClassInfo>(0);
}
