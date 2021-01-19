package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class CourseChapterVideoPK implements Serializable {
	@Column(name = "course_id")
	Integer courseId;
	
	@Column(name = "chapter_id")
	Integer chapterId;
	
	@Column(name = "video_id")
	Integer videoId;
}
