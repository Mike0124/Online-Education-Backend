package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
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
	
	@Column(name = "nickname")
	private String nickName;
	
	@Column(name = "phone_id", nullable = false, unique = true)
	@JsonIgnore
	private String phoneId;
	
	@Column
	private String sex;
	
	@Column
	private String school;
	
	@Column(name = "major_id")
	private Integer majorId;
	
	@Column
	private String grade;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Column(name = "is_vip")
	private boolean isVip;
	
	@Column(name = "student_pic_url")
	private String studentPicUrl;
	
	@ManyToOne
	@JoinColumn(name = "major_id", referencedColumnName = "major_id", insertable = false, updatable = false)
	Major major;
}


