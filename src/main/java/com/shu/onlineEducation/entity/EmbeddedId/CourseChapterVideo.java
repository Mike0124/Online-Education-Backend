package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course_chapter_video")
public class CourseChapterVideo {
	@EmbeddedId
	private CourseChapterVideoPK courseChapterVideoPK;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id", insertable = false, updatable = false)
	})
	CourseChapter courseChapter;
	
	@Column(name = "video_url")
	String videoUrl;
	
	@Column(name = "video_name")
	String videoName;
}
