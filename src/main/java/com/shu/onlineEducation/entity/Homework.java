package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "homework")
public class Homework {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homework_id")
	Integer homeworkId;
	
	@Column(name = "student_id")
	Integer studentId;
	
	@Column(name = "task_id")
	Integer taskId;
	
	@Column(name = "course_id")
	Integer courseId;
	
	@Column(name = "chapter_id")
	Integer chapterId;
	
	Integer likes;
	
	String content;
	
	@Column(name = "commit_time")
	Timestamp commitTime;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id", insertable = false, updatable = false)
	})
	CourseChapter courseChapter;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "task_id", referencedColumnName = "task_id", insertable = false, updatable = false)
	private Task task;
}
