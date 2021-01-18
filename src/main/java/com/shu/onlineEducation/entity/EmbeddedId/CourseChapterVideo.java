package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course_chapter_video")
public class CourseChapterVideo {
	@EmbeddedId
	private CourseChapterVideoPK courseChapterVideoPK;
	
	@Column(name = "video_url")
	String videoUrl;
	
	@Column(name = "video_name")
	String videoName;
}
