package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teacher_comment")
public class TeacherComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;
	
	@Column
	private Integer likes;
	@Column
	private String content;
	
	@CreatedDate
	@Column
	private Timestamp time;

	@Column(name = "comment_mark")
	private Integer commentMark;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "teacher_id")
	private Integer teacherId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id",insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "teacher_id",referencedColumnName = "user_id", insertable = false, updatable = false)
	private Teacher teacher;
}
