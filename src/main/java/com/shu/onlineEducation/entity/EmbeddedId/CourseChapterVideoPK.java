package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class CourseChapterVideoPK implements Serializable {
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id"),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
	})
	CourseChapter courseChapter;
	
	@Column(name = "video_id")
	private Integer videoId;
}
