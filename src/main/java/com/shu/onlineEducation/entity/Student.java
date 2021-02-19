package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
	
	@Column(name = "vip_date")
	private Timestamp vipDate;
	
	@Column(name = "student_pic_url")
	private String studentPicUrl;
	
	@ManyToOne
	@JoinColumn(name = "major_id", referencedColumnName = "major_id", insertable = false, updatable = false)
	Major major;
	
	public String getVipDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vipDate);
	}
}


