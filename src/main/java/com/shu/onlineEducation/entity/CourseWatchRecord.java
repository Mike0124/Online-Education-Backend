package com.shu.onlineEducation.entity;

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
	
	@ManyToOne
	@JoinColumn(name = "student_id",referencedColumnName = "user_id")
	private Student student;
	
	@Column(name = "watch_time")
	Timestamp watchTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
			// 分别匹配主表联合主键的两个字段
			@JoinColumn(name="course_id", referencedColumnName="course_id"),
			@JoinColumn(name="chapter_id", referencedColumnName="chapter_id"),
			@JoinColumn(name="video_id", referencedColumnName="video_id")
	})
	CourseChapterVideo courseChapterVideo;
	
}
