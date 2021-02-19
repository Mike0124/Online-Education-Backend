package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
	
	@Column
	Integer mark;
	
	@Column
	String reply;
	
	@Column
	Integer likes;
	
	@Column
	String content;
	
	@Column(name = "commit_time")
	Timestamp commitTime;
	
	@Column
	Integer status;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "task_id", referencedColumnName = "task_id", insertable = false, updatable = false)
	private Task task;
	
	public String getCommitTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commitTime);
	}
}
