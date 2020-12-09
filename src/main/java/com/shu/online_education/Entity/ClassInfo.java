package com.shu.online_education.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "class")
public class ClassInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;
    @Column(nullable = false)
    private String name;
    @Column(name = "class_num",nullable = false)
    private int classNum;
    @Column(nullable = false)
    private String intro;
    @Column(nullable = false)
    private Date upload_time;
    @Column(name = "prefer_id",nullable = false)
    private int preferId;
    @ManyToMany(mappedBy = "ClassInfos")
    @JsonIgnore
    private Set<StudentInfo> studentInfos = new HashSet<StudentInfo>(0);
}
