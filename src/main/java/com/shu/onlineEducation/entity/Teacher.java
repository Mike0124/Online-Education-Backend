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
	
	//	@ManyToOne
//	@JoinColumn(name = "major_id", referencedColumnName = "major_id")
//	Major major;
	
	@Column(name = "major_id")
	private String majorId;
	
	@Column
	private String password;
	
	@Column(name = "teacher_pic_url")
	private String teacherPicUrl;
	
	@Column(name = "teacher_status")
	private Integer teacherStatus;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private Set<Course> courseSet;
	
}
