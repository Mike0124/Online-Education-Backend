package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course_comment")
public class CourseComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;
	
	@Column
	private Integer likes;
	
	@Column
	private String content;
	
	@CreatedDate
	@LastModifiedDate
	@Column
	private Timestamp time;
	
	@Column(name = "comment_mark")
	private Integer commentMark;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name = "course_id")
	private Integer courseId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private Course course;
}
