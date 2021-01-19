package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "course_watch_record")
public class CourseWatchRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "watch_time")
	Timestamp watchTime;

	@Column(name = "student_id")
	Integer studentId;

	@Column(name = "course_id")
	Integer courseId;

	@Column(name = "chapter_id")
	Integer chapterId;

	@Column(name = "video_id")
	Integer videoId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id",referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
			// 分别匹配主表联合主键的两个字段
			@JoinColumn(name="course_id", referencedColumnName="course_id", insertable = false, updatable = false),
			@JoinColumn(name="chapter_id", referencedColumnName="chapter_id", insertable = false, updatable = false),
			@JoinColumn(name="video_id", referencedColumnName="video_id", insertable = false, updatable = false)
	})
	CourseChapterVideo courseChapterVideo;
	
}
