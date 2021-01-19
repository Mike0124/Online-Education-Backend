package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
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
	
	@Column
	private Timestamp time;
	
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "user_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id",referencedColumnName = "user_id")
	private Teacher teacher;
	
	@Column(name = "comment_mark")
	private int commentMark;
}
