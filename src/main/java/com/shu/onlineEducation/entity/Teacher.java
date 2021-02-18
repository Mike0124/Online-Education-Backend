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
@Table(name = "teachers", uniqueConstraints = @UniqueConstraint(columnNames = "phone_id"))
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	
	@Column
	private String name;
	
	@Column(name = "phone_id", nullable = false)
	@JsonIgnore
	private String phoneId;
	
	@Column
	private String sex;
	
	@Column
	private String school;
	
	@Column(name = "major_id")
	private Integer majorId;
	
	@Column
	private String intro;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Column(name = "teacher_pic_url")
	private String teacherPicUrl;
	
	@Column(name = "teacher_status")
	private Integer teacherStatus;

	@ManyToOne
	@JoinColumn(name = "major_id", referencedColumnName = "major_id", updatable = false, insertable = false)
	Major major;
}
