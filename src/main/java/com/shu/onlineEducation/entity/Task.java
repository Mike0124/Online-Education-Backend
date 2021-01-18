package com.shu.onlineEducation.entity;

import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	Integer taskId;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id"),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
	})
	CourseChapter courseChapter;
	
	@Column(name = "task_name")
	String taskName;
	
	String content;
	
	@Column(name = "start_time")
	Timestamp startTime;
	
	@Column(name = "end_time")
	Timestamp endTime;
	
	public String getStartTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(startTime);
	}
	
	public String getEndTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(endTime);
	}
	
}
