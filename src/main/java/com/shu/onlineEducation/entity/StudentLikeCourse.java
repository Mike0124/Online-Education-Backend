package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stu_like_course")
public class StudentLikeCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	
	@Column(name = "course_id")
	private Integer courseId;
	
	@CreatedDate
	@Column(name = "time")
	private Timestamp time;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private Course course;
	
	public String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
}
