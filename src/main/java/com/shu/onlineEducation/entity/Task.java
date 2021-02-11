package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
	
	@Column(name = "task_name")
	String taskName;
	
	String content;
	
	@Column(name = "start_time")
	Timestamp startTime;
	
	@Column(name = "end_time")
	Timestamp endTime;

	@Column(name = "course_id")
	Integer courseId;

	@Column(name = "chapter_id")
	Integer chapterId;

	@ManyToOne
	@JsonIgnore
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id", updatable = false, insertable = false),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id", updatable = false, insertable = false)
	})
	CourseChapter courseChapter;
	
	public String getStartTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(startTime);
	}
	
	public String getEndTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(endTime);
	}
	
}
